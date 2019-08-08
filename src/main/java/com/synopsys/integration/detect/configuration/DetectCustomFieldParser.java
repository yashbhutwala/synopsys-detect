/**
 * synopsys-detect
 *
 * Copyright (c) 2019 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.detect.configuration;

import java.util.Map;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;

import com.synopsys.integration.detect.exception.DetectUserFriendlyException;
import com.synopsys.integration.detect.exitcode.ExitCodeType;
import com.synopsys.integration.detect.workflow.blackduck.CustomFieldDocument;

public class DetectCustomFieldParser {

    public CustomFieldDocument parseCustomFieldDocument(Map<String, String> currentProperties) throws DetectUserFriendlyException {
        try {
            ConfigurationPropertySource source = new MapConfigurationPropertySource(currentProperties);
            Binder objectBinder = new Binder(source);
            BindResult<CustomFieldDocument> fieldDocumentBinding = objectBinder.bind("detect.custom.fields", CustomFieldDocument.class);
            return fieldDocumentBinding.orElse(new CustomFieldDocument());
        } catch (Exception e) {
            throw new DetectUserFriendlyException("Unable to parse custom fields.", e, ExitCodeType.FAILURE_CONFIGURATION);
        }
    }
}