package info.chili.service.jrs;

import info.chili.service.jrs.types.Error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class ServiceMessages {

    private List<Error> errors = new ArrayList<Error>();

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public void addError(Error error) {
        this.errors.add(error);
    }

    public void clear() {
        errors.clear();
    }

    public boolean isNotEmpty() {
        return !errors.isEmpty();
    }
}
