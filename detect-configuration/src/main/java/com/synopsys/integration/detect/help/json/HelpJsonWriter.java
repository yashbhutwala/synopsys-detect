/**
 * detect-configuration
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
package com.synopsys.integration.detect.help.json;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.synopsys.integration.detect.configuration.DetectProperty;
import com.synopsys.integration.detect.help.DetectOption;
import com.synopsys.integration.detect.help.DetectOptionDeprecation;
import com.synopsys.integration.detect.help.DetectOptionMetaData;
import com.synopsys.integration.detect.help.html.HelpHtmlWriter;

import freemarker.template.Configuration;

public class HelpJsonWriter {
    private final Logger logger = LoggerFactory.getLogger(HelpHtmlWriter.class);

    private final Configuration configuration;
    private final Gson gson;

    public HelpJsonWriter(final Configuration configuration, final Gson gson) {
        this.configuration = configuration;
        this.gson = gson;
    }

    public void writeGsonDocument(final String filename, final List<DetectOption> detectOptions) {
        final HelpJsonData data = new HelpJsonData();

        for (final DetectOption option : detectOptions) {
            final HelpJsonOption helpJsonOption = convertOption(option);
            data.options.add(helpJsonOption);
        }

        try {
            try (final Writer writer = new FileWriter(filename)) {
                gson.toJson(data, writer);
            }

            logger.info(filename + " was created in your current directory.");
        } catch (final IOException e) {
            logger.error("There was an error when creating the html file", e);
        }
    }

    public HelpJsonOption convertOption(final DetectOption detectOption) {
        final HelpJsonOption helpJsonOption = new HelpJsonOption();

        helpJsonOption.strictValues = detectOption.hasStrictValidation();
        helpJsonOption.caseSensitiveValues = detectOption.hasCaseSensitiveValidation();
        helpJsonOption.acceptableValues = detectOption.getValidValues();
        helpJsonOption.hasAcceptableValues = detectOption.getValidValues().size() > 0;
        helpJsonOption.isCommaSeparatedList = detectOption.isCommaSeperatedList();

        final DetectProperty property = detectOption.getDetectProperty();
        helpJsonOption.propertyKey = property.getPropertyKey();
        helpJsonOption.propertyType = property.getPropertyType().getDisplayName();
        helpJsonOption.defaultValue = property.getDefaultValue();

        final DetectOptionMetaData optionHelp = detectOption.getDetectOptionMetaData();
        helpJsonOption.propertyName = optionHelp.name;
        helpJsonOption.addedInVersion = optionHelp.fromVersion;
        helpJsonOption.group = optionHelp.primaryGroup;
        helpJsonOption.additionalGroups = optionHelp.additionalGroups;
        helpJsonOption.description = optionHelp.help;
        helpJsonOption.detailedDescription = optionHelp.helpDetailed;

        final DetectOptionDeprecation optionDeprecation = detectOption.getDetectOptionDeprecation();
        helpJsonOption.deprecated = optionDeprecation.isDeprecated;
        if (optionDeprecation.isDeprecated) {
            helpJsonOption.deprecatedDescription = optionDeprecation.deprecation;
            helpJsonOption.deprecatedFailInVersion = optionDeprecation.deprecationFailInVersion.getDisplayValue();
            helpJsonOption.deprecatedRemoveInVersion = optionDeprecation.deprecationRemoveInVersion.getDisplayValue();
        }

        return helpJsonOption;
    }
}
