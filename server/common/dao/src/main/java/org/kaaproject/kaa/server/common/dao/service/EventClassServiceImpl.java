/*
 * Copyright 2014-2016 CyberVision, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kaaproject.kaa.server.common.dao.service;

import static org.kaaproject.kaa.server.common.dao.impl.DaoUtil.convertDtoList;
import static org.kaaproject.kaa.server.common.dao.impl.DaoUtil.getDto;
import static org.kaaproject.kaa.server.common.dao.service.Validator.isValidSqlId;
import static org.kaaproject.kaa.server.common.dao.service.Validator.isValidSqlObject;
import static org.kaaproject.kaa.server.common.dao.service.Validator.validateSqlId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.kaaproject.kaa.common.dto.event.EventClassDto;
import org.kaaproject.kaa.common.dto.event.EventClassFamilyDto;
import org.kaaproject.kaa.common.dto.event.EventClassFamilyVersionDto;
import org.kaaproject.kaa.common.dto.event.EventClassType;
import org.kaaproject.kaa.server.common.dao.EventClassService;
import org.kaaproject.kaa.server.common.dao.exception.IncorrectParameterException;
import org.kaaproject.kaa.server.common.dao.impl.EventClassDao;
import org.kaaproject.kaa.server.common.dao.impl.EventClassFamilyDao;
import org.kaaproject.kaa.server.common.dao.model.sql.EventClass;
import org.kaaproject.kaa.server.common.dao.model.sql.EventClassFamily;
import org.kaaproject.kaa.server.common.dao.model.sql.EventClassFamilyVersion;
import org.kaaproject.kaa.server.common.dao.schema.EventSchemaException;
import org.kaaproject.kaa.server.common.dao.schema.EventSchemaMetadata;
import org.kaaproject.kaa.server.common.dao.schema.EventSchemaProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventClassServiceImpl implements EventClassService {

    private static final Logger LOG = LoggerFactory.getLogger(EventClassServiceImpl.class);

    @Autowired
    private EventClassFamilyDao<EventClassFamily> eventClassFamilyDao;

    @Autowired
    private EventClassDao<EventClass> eventClassDao;

    @Autowired
    private EventSchemaProcessor eventSchemaProcessor;

    @Override
    public List<EventClassFamilyDto> findEventClassFamiliesByTenantId(
            String tenantId) {
        List<EventClassFamilyDto> eventClassFamilies;
        if (isValidSqlId(tenantId)) {
            LOG.debug("Find event class families by tenant id [{}]", tenantId);
            eventClassFamilies = convertDtoList(eventClassFamilyDao.findByTenantId(tenantId));
        } else {
            throw new IncorrectParameterException("Incorrect tenant id: " + tenantId);
        }
        return eventClassFamilies;
    }

    @Override
    public EventClassFamilyDto findEventClassFamilyByTenantIdAndName(String tenantId, String name) {
        if (isValidSqlId(tenantId)) {
            LOG.debug("Find event class family by tenant id [{}] and name {}", tenantId, name);
            return eventClassFamilyDao.findByTenantIdAndName(tenantId, name).toDto();
        } else {
            throw new IncorrectParameterException("Incorrect tenant id: " + tenantId);
        }
    }

    @Override
    public EventClassFamilyDto findEventClassFamilyById(String id) {
        validateSqlId(id, "Event class family id is incorrect. Can't find event class family by id " + id);
        return getDto(eventClassFamilyDao.findById(id));
    }

    @Override
    public List<EventClassFamilyVersionDto> findEventClassFamilyVersionsById(String id) {
        validateSqlId(id, "Event class family id is incorrect. Can't find event class family by id " + id);
        EventClassFamily ecf = eventClassFamilyDao.findById(id);
        return convertDtoList(ecf.getSchemas());
    }

    @Override
    public EventClassFamilyDto saveEventClassFamily(
            EventClassFamilyVersionDto eventClassFamilyVersionDto) {
        EventClassFamilyDto savedEventClassFamilyDto = null;
        if (isValidSqlObject(eventClassFamilyVersionDto)) {
            savedEventClassFamilyDto = getDto(eventClassFamilyDao.findById(eventClassFamilyVersionDto.getId()));
            savedEventClassFamilyDto.setCreatedTime(System.currentTimeMillis());
            EventClassFamily ecf = new EventClassFamily(savedEventClassFamilyDto);
            List<EventClassFamilyVersion> schemas = new ArrayList<>();
            findEventClassFamilyVersionsById(eventClassFamilyVersionDto.getId()).forEach(s -> schemas.add(new EventClassFamilyVersion(s)));
            schemas.add(new EventClassFamilyVersion(eventClassFamilyVersionDto));
            ecf.setSchemas(schemas);
            savedEventClassFamilyDto = getDto(eventClassFamilyDao.save(ecf));
        }
        return savedEventClassFamilyDto;
    }

    @Override
    public void addEventClassFamilySchema(String eventClassFamilyId,
            String eventClassFamilySchema, String createdUsername) {
        EventClassFamilyDto eventClassFamily = findEventClassFamilyById(eventClassFamilyId);
        if (eventClassFamily != null) {
            List<EventSchemaMetadata> eventSchemas;
            try {
                eventSchemas = eventSchemaProcessor.processSchema(eventClassFamilySchema);
            } catch (EventSchemaException e) {
                LOG.warn("Can't process event class family schema.", e);
                throw new IncorrectParameterException("Incorrect event class family schema.");
            }
            List<String> fqns = new ArrayList<>(eventSchemas.size());
            for (EventSchemaMetadata eventSchema : eventSchemas) {
                fqns.add(eventSchema.getFqn());
            }
            if (validateEventClassFamilyFqns(eventClassFamily, fqns)) {
                List<EventClassFamilyVersionDto> schemasDto = findEventClassFamilyVersionsById(eventClassFamilyId);
                int version = 1;
                if (schemasDto != null && !schemasDto.isEmpty()) {
                    Collections.sort(schemasDto, new Comparator<EventClassFamilyVersionDto>() {
                        @Override
                        public int compare(EventClassFamilyVersionDto o1,
                                           EventClassFamilyVersionDto o2) {
                            return o1.getVersion() - o2.getVersion();
                        }
                    });
                    version = schemasDto.get(schemasDto.size()-1).getVersion()+1;
                }
                EventClassFamilyVersionDto eventClassFamilyVersion = new EventClassFamilyVersionDto();
                eventClassFamilyVersion.setVersion(version);
                eventClassFamilyVersion.setCreatedTime(System.currentTimeMillis());
                eventClassFamilyVersion.setCreatedUsername(createdUsername);
                schemasDto.add(eventClassFamilyVersion);
                EventClassFamily ecf = new EventClassFamily(eventClassFamily);
                List<EventClassFamilyVersion> schemas = new ArrayList<>();
                schemasDto.forEach(s -> schemas.add(new EventClassFamilyVersion(s)));
                ecf.setSchemas(schemas);
                eventClassFamilyDao.save(new EventClassFamily(eventClassFamily));
                for (EventSchemaMetadata eventSchema : eventSchemas) {
                    saveEventClassSchema(eventClassFamily, eventSchema, version);
                }
            } else {
                LOG.debug("Can't process event class family schema.");
                throw new IncorrectParameterException("Incorrect event class family schema. FQNs should be unique within the tenant.");
            }
        } else {
            LOG.debug("Can't find related event class family.");
            throw new IncorrectParameterException("Event class family not found, id:" + eventClassFamilyId);
        }
    }

    private void saveEventClassSchema(EventClassFamilyDto eventClassFamilyDto, EventSchemaMetadata eventSchema, int version) {
        EventClassDto eventClass = new EventClassDto();
        eventClass.setTenantId(eventClassFamilyDto.getTenantId());
        eventClass.setEcfId(eventClassFamilyDto.getId());
        eventClass.setFqn(eventSchema.getFqn());
        eventClass.setType(eventSchema.getType());
        eventClass.setVersion(version);
        eventClass.setCtlSchemaId(eventSchema.getCtlSchemaId());
        eventClassDao.save(new EventClass(eventClass));
    }

    private boolean validateEventClassFamilyFqns(EventClassFamilyDto eventClassFamily, List<String> fqns) {
        return eventClassDao.validateFqns(eventClassFamily.getTenantId(), eventClassFamily.getId(), fqns);
    }

    @Override
    public List<EventClassDto> findEventClassesByFamilyIdVersionAndType(String ecfId, int version, EventClassType type) {
        List<EventClassDto> eventClasses;
        if (isValidSqlId(ecfId)) {
            LOG.debug("Find event classes by family id [{}] version [{}] and type [{}]", ecfId, version, type);
            eventClasses = convertDtoList(eventClassDao.findByEcfIdVersionAndType(ecfId, version, type));
        } else {
            throw new IncorrectParameterException("Incorrect event class family id: " + ecfId);
        }
        return eventClasses;
    }

    @Override
    public List<EventClassDto> findEventClassByTenantIdAndFQN(String tenantId, String fqn) {
        if (isValidSqlId(tenantId)) {
            LOG.debug("Find event class family by tenant id [{}] and fqn {}", tenantId, fqn);
            return convertDtoList(eventClassDao.findByTenantIdAndFqn(tenantId, fqn));
        } else {
            throw new IncorrectParameterException("Incorrect tenant id: " + tenantId);
        }
    }

    @Override
    public EventClassDto findEventClassByTenantIdAndFQNAndVersion(String tenantId, String fqn, int version) {
        if (isValidSqlId(tenantId)) {
            LOG.debug("Find event class family by tenant id [{}] and fqn {}", tenantId, fqn);
            return getDto(eventClassDao.findByTenantIdAndFqnAndVersion(tenantId, fqn, version));
        } else {
            throw new IncorrectParameterException("Incorrect tenant id: " + tenantId);
        }

    }


}
