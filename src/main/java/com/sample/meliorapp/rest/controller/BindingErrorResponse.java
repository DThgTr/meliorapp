package com.sample.meliorapp.rest.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class BindingErrorResponse {
    //BindingError implementation
    protected static class BindingError {
        private String objectName;      //Object associated with the binding error
        private String fieldName;       //Field name experiencing the binding error
        private String fieldValue;      //Value of the field name
        private String errorMessage;

        public BindingError() {
            this.objectName = "";
            this.fieldName = "";
            this.fieldValue = "";
            this.errorMessage = "";
        }

        protected void setObjectName(String objectName) {
            this.objectName = objectName;
        }

        protected void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        protected void setFieldValue(String fieldValue) {
            this.fieldValue = fieldValue;
        }

        protected void setErrorMessage(String error_message) {
            this.errorMessage = error_message;
        }

        @Override
        public String toString() {
            return "BindingError [objectName=" + objectName + ", fieldName=" + fieldName + ", fieldValue=" + fieldValue
                    + ", errorMessage=" + errorMessage + "]";
        }

    }
    //====================================================================================
    public BindingErrorResponse(Integer pathId, Integer bodyId) {
        // bodyId given without pathId
        boolean onlyBodyIdSpecified = (pathId == null) && (bodyId != null);
        if (onlyBodyIdSpecified) {
            addBodyIdError(bodyId, "must not be specified");
        }
        // Mismatch pathId & bodyId
        boolean bothIdsSpecified = (pathId != null) && (bodyId != null);
        if (bothIdsSpecified && !pathId.equals(bodyId)) {
            addBodyIdError(bodyId, String.format("does not match pathId: %d", pathId));
        }
    }

    //None
    public BindingErrorResponse() {
        this(null);
    }
    //bodyId specified
    public BindingErrorResponse(Integer id) {
        this(null, id);
    }
    private void addBodyIdError(Integer bodyId, String message) {
        BindingError error = new BindingError();
        error.setObjectName("body");
        error.setFieldName("id");
        error.setFieldValue(bodyId.toString());
        error.setErrorMessage(message);
        addError(error);
    }

    private final List<BindingError> bindingErrors = new ArrayList<BindingError>();

    public void addError(BindingError bindingError) {
        this.bindingErrors.add(bindingError);
    }

    public void addAllErrors(BindingResult bindingResult) {
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            BindingError error = new BindingError();
            error.setObjectName(fieldError.getObjectName());
            error.setFieldName(fieldError.getField());
            error.setFieldValue(String.valueOf(fieldError.getRejectedValue()));
            error.setErrorMessage(fieldError.getDefaultMessage());
            addError(error);
        }
    }

    public String toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String errorsAsJSON = "";
        try {
            errorsAsJSON = mapper.writeValueAsString(bindingErrors);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return errorsAsJSON;
    }

    @Override
    public String toString() {
        return "BindingErrorsResponse [bindingErrors=" + bindingErrors + "]";
    }

}
