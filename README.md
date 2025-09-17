
![Logo](https://www.kanopus.cl/admin/javax.faces.resource/images/logo-gray.png.xhtml?ln=paradise-layout)


# ktool-local-service

This project is designed to run on a client machine and serve as data exchange from a web page to the client computer. In this way you can send information directly from the web page to the client's local printer.

To review all the available methods once the service has started, you can try it through the automatic documentation created with "Swagger" at the following URL:
http://localhost:1982/local-services/swagger-ui/

## Usage/Examples

```javascript

<script type="text/javascript" src="printer.js"></script>
const localServices = new LocalServices("http://localhost:1982/local-services");

//Print with thermal
var base64 = ".......";
localServices.printThermal(base64);

//Show printers connected to the computer
localServices.showPrinters();
```


## Authors

- [@pabloandres.diazsaavedra](https://www.linkedin.com/in/pablo-diaz-saavedra-4b7b0522/)


## License

This software is licensed under the Apache License, Version 2.0. See the LICENSE file for details.
I hope you enjoy it.

[![Apache License, Version 2.0](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://opensource.org/license/apache-2-0)

## Support

For support, email soporte@kanopus.cl
