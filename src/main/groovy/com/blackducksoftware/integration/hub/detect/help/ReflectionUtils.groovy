/*
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
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
package com.blackducksoftware.integration.hub.detect.help;

import java.lang.reflect.Field;

import org.apache.commons.lang3.math.NumberUtils


public class ReflectionUtils {

    public static void setValue(final Field field, final Object obj, final String value){
        final Class type = field.getType()
        Object fieldValue = field.get(obj)
        if (String.class == type) {
            field.set(obj, value)
        } else if (Integer.class == type) {
            field.set(obj, NumberUtils.toInt(value))
        } else if (Long.class == type) {
            field.set(obj, NumberUtils.toLong(value))
        } else if (Boolean.class == type) {
            field.set(obj, Boolean.parseBoolean(value))
        }
    }

    public static boolean isValueNull(final Field field, final Object obj){
        final Class type = field.getType()
        Object fieldValue = field.get(obj)
        if (String.class == type && !(fieldValue as String)?.trim()) {
            return true;
        } else if (Integer.class == type && fieldValue == null) {
            return true;
        } else if (Long.class == type && fieldValue == null) {
            return true;
        } else if (Boolean.class == type && fieldValue == null) {
            return true;
        }
        return false;
    }
}