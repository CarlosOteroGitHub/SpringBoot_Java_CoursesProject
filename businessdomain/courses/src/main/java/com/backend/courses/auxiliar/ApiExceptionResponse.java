package com.backend.courses.auxiliar;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApiExceptionResponse {

    @ApiModelProperty(notes = "Titulo del error", name = "title", required = true, example = "El usuario no tiene credenciales")
    private String title;

    @ApiModelProperty(notes = "Código de error", name = "code", required = false, example = "192")
    private String code;
    
    @ApiModelProperty(notes = "Tipo de Error", name = "type", required = true)
    private String type = "/errors/uncategorized";

    @ApiModelProperty(notes = "Descripción del error", name = "detail", required = true, example = "The user does not have the propertly persmissions to acces the resource, please contact with ass https://digitalthinking.biz/es/ada-enterprise-core#contactus")
    private String detail;


    public ApiExceptionResponse(String title, String code, String detail) {
        super();
        this.title = title;
        this.code = code;
        this.detail = detail;
    }
}
