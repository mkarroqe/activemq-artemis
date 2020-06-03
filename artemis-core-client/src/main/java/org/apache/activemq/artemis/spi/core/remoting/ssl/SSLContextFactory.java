/*
 * Copyright 2020 The Apache Software Foundation.
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
package org.apache.activemq.artemis.spi.core.remoting.ssl;

import java.util.Map;
import javax.net.ssl.SSLContext;
import org.jboss.logging.Logger;

/**
 * Service interface to create a SSLContext for a configuration.
 * This is NOT used by OpenSSL.
 * To create and use your own implementation you need to create a file
 * <code>META-INF/services/org.apache.activemq.artemis.spi.core.remoting.ssl.SSLContextFactory</code>
 * in your jar and fill it with the full qualified name of your implementation.
 */
public interface SSLContextFactory extends Comparable<SSLContextFactory> {
   Logger log = Logger.getLogger(SSLContextFactory.class);

   /**
    * Obtain a SSLContext from the configuration.
    * @param configuration
    * @param keystoreProvider
    * @param keystorePath
    * @param keystorePassword
    * @param truststoreProvider
    * @param truststorePath
    * @param truststorePassword
    * @param crlPath
    * @param trustManagerFactoryPlugin
    * @param trustAll
    * @return a SSLContext instance.
    * @throws Exception
    */
   SSLContext getSSLContext(Map<String, Object> configuration,
           String keystoreProvider, String keystorePath, String keystorePassword,
           String truststoreProvider, String truststorePath, String truststorePassword,
           String crlPath, String trustManagerFactoryPlugin, boolean trustAll) throws Exception;

   default void clearSSLContexts() {
   }

   /**
    * The priority for the SSLContextFactory when resolving the service to get the implementation.
    * This is used when selecting the implementation when several implementations are loaded.
    * The highest priority implementation will be used.
    * @return the priority.
    */
   int getPriority();

   @Override
   default int compareTo(SSLContextFactory other) {
      return this.getPriority() - other.getPriority();
   }
}