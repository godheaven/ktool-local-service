/** 
 * Local Services - Printer
 * @author: Pablo Diaz Saavedra
 */
const DIALOG_PRINTERS_TITLE = 'Seleccione Impresora';
const DIALOG_PRINTERS_LABEL_SELECTOR = '--- SELECCIONE ---';
const DIALOG_PRINTERS_BODY = ''
        + '<div id="local-services-dialog" style="display: none; padding-top: 15px">'
        + '  <div id="local-services-dialog-data">'
        + '    <p>Debe seleccionar una impresora para imprimir el documento:</p>'
        + '    <div><select id="local-services-combobox-printers" style="width: 100%"></select></div>'
        + '    <div style="text-align:center; padding-top: 15px"><button onclick="localServices.setPrinterName($(\'#local-services-combobox-printers\').val()); $(\'#local-services-dialog\').dialog(\'close\')">Aceptar</button></div>'
        + '  </div>'
        + '</div>';

const DIALOG_PRINTERS_NOT_FOUND_TITLE = 'No existen impresoras configuradas';
const DIALOG_PRINTERS_NOT_FOUND_DATA = 'No se encontraron impresoras configuradas en el computador.<br/>Por favor verifique su configuración.';

const DIALOG_SERVICE_NOT_RUNNING_TITLE = 'Servicio local no esta ejecutandose.';
const DIALOG_SERVICE_NOT_RUNNING_DATA = 'No es posible conectarse al servicio local de impresoras.<br/>Por favor verifique que el servicio este ejecutandose.';

class LocalServices {

    constructor(url) {
        this.url = url;
        this.name = '';
    }

    printThermal(base64) {
        var name = window.localStorage.getItem('local_services_printer');
        if (name !== null) {
            $.ajax({
                type: "POST",
                url: this.url + "/print/thermal-pdf",
                contentType: "application/json; charset=UTF-8",
                data: JSON.stringify({dataBase64: base64, printer: name}),
                error: function (request, status, error) {
                    $("#local-services-dialog-data").html(DIALOG_SERVICE_NOT_RUNNING_DATA);
                    $("#local-services-dialog").dialog({title: DIALOG_SERVICE_NOT_RUNNING_TITLE, width: "450px"});
                }
            });
        } else {
            this.showPrinters();
        }
    }

    showPrinters() {

        $.ajax({
            type: "GET",
            url: this.url + "/printers",
            success: function (data) {
                if (data.length > 0) {
                    var name = window.localStorage.getItem('local_services_printer');
                    var s = '<option value="-1">' + DIALOG_PRINTERS_LABEL_SELECTOR + '</option>';
                    for (var i = 0; i < data.length; i++) {
                        if (data[i] === name) {
                            s += '<option value="' + data[i] + '" selected>' + data[i] + '</option>';
                        } else {
                            s += '<option value="' + data[i] + '">' + data[i] + '</option>';
                        }

                    }
                    $("#local-services-combobox-printers").html(s);
                    $("#local-services-dialog").dialog({title: DIALOG_PRINTERS_TITLE, width: "400px"});
                } else {
                    $("#local-services-dialog-data").html(DIALOG_PRINTERS_NOT_FOUND_DATA);
                    $("#local-services-dialog").dialog({title: DIALOG_PRINTERS_NOT_FOUND_TITLE, width: "450px"});
                }
            },
            error: function (request, status, error) {
                $("#local-services-dialog-data").html(DIALOG_SERVICE_NOT_RUNNING_DATA);
                $("#local-services-dialog").dialog({title: DIALOG_SERVICE_NOT_RUNNING_TITLE, width: "450px"});
            }
        });
    }

    setPrinterName(name) {
        window.localStorage.setItem('local_services_printer', name);
    }
}

$(document).ready(function () {
    $(DIALOG_PRINTERS_BODY).appendTo('body');
});