/*-
 * !--
 * For support and inquiries regarding this library, please contact:
 *   soporte@kanopus.cl
 * 
 * Project website:
 *   https://www.kanopus.cl
 * %%
 * Copyright (C) 2025 - 2026 Pablo Díaz Saavedra
 * %%
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
 * --!
 */
package cl.kanopus.local.application;

import cl.kanopus.local.application.enums.Property;
import java.util.Date;
import java.util.TimeZone;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 *
 * @author Pablo Diaz Saavedra
 * @email pabloandres.diazsaavedra@gmail.com
 */
@Component
public class MyEnvironment {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyEnvironment.class);

    @Autowired
    private Environment environment;

    @PostConstruct
    public void onStart() throws Exception {

        LOGGER.debug("Local services running in " + TimeZone.getDefault().getDisplayName() + " timezone :" + new Date());
        StringBuilder info = new StringBuilder();

        info.append("\n");
        info.append("██   ██ ████████  ██████   ██████  ██      ███████ \n");
        info.append("██  ██     ██    ██    ██ ██    ██ ██      ██     \n");
        info.append("█████      ██    ██    ██ ██    ██ ██      ███████\n");
        info.append("██  ██     ██    ██    ██ ██    ██ ██           ██\n");
        info.append("██   ██    ██     ██████   ██████  ███████ ███████\n");

        info.append("\n");
        info.append(" :: Ktool Local Services             ::  (v1.0.0) \n");
        info.append(" :: Author                           ::  Pablo Diaz Saavedra \n");

        LOGGER.debug(info.toString());
    }

    public String getProp(Property property) {
        return environment.getProperty(property.getValue());
    }

    public boolean getPropBoolean(Property property) {
        return environment.getProperty(property.getValue(), Boolean.class);
    }

    public long getPropLong(Property property) {
        return environment.getProperty(property.getValue(), Long.class);
    }

    public int getPropInteger(Property property) {
        return environment.getProperty(property.getValue(), Integer.class);
    }

    public String getNativeProp(String property) {
        return environment.getProperty(property);
    }

    public Long getNativePropLong(String property) {
        return environment.getProperty(property, Long.class);
    }

    public Integer getNativePropInteger(String property) {
        return environment.getProperty(property, Integer.class);
    }

}
