<p style="text-align:left">
  <img src="https://www.kanopus.cl/assets/kanopus_black.png" width="220" alt="Kanopus logo"/>
</p>

![Maven](https://img.shields.io/maven-central/v/cl.kanopus.tool/ktool-local-service) ![License](https://img.shields.io/badge/license-Apache%20License%202.0-blue) ![Java](https://img.shields.io/badge/java-17+-orange)

# ktool-local-service

This project is designed to run on a client machine and serve as data exchange from a web page to the client computer. In this way you can send information directly from the web page to the client's local printer.

To review all the available methods once the service has started, you can try it through the automatic documentation created with "Swagger" at the following URL:
http://localhost:1982/local-services/swagger-ui/

## ✨ Features

- Ready-to-run automation tool for the Kanopus ecosystem
- Supports command-line execution for developer workflows
- Suitable for Docker-based execution in controlled environments
- Designed to improve productivity and operational consistency

## 🚀 Installation

You can run this tool using Docker or from the command line.

**Option 1 — Docker**

```bash
docker run --rm ktool-local-service:4.05.0
```

**Option 2 — Command line**

```bash
java -jar ktool-local-service-4.05.0.jar
```

## ⚙️ Configurable properties

| Property | Description | Default |
| --- | --- | --- |
| `server.compression.enabled` | Configuration property | `true` |
| `server.compression.mime-types` | Configuration property | `text/html,text/plain,text/css,application/javascript,application/json` |
| `server.error.include-stacktrace` | Configuration property | `never` |
| `server.port` | Configuration property | `1982` |
| `server.servlet.context-path` | Configuration property | `/local-services` |
| `spring.jackson.date-format` | Configuration property | `yyyy-MM-dd'T'HH:mm:ss` |
| `spring.jackson.default-property-inclusion` | Configuration property | `non_null` |
| `spring.jackson.time-zone` | Configuration property | `America/Santiago` |

## 🚀 Usage Guide

Example command:

```bash
java -jar ktool-local-service-4.05.0.jar
```

Replace this example with the real command-line parameters supported by the tool.

## 👤 Author

⭐**Pablo Andrés Díaz Saavedra** — Founder of **Kanopus – Software Guided by the Stars**⭐

Kanopus is building a constellation of developers creating tools, libraries and platforms that simplify software engineering.

[GitHub](https://github.com/godheaven) | [LinkedIn](https://www.linkedin.com/in/pablo-diaz-saavedra-4b7b0522/) | [Website](https://kanopus.cl)

## 📄 License

This software is licensed under the Apache License, Version 2.0. See the LICENSE file for details.
I hope you enjoy it.

[![Apache License, Version 2.0](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://opensource.org/license/apache-2-0)

## 🛟 Support

For support or questions contact: 📧 [soporte@kanopus.cl](mailto:soporte@kanopus.cl)
