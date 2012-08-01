package info.chili.service.jrs.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@SuppressWarnings("restriction")
@XmlType
@XmlRootElement(name = "Errors")
public class Errors implements Serializable {

	private static final long serialVersionUID = 1L;

	public Errors() {
	}

	@XmlElement(name = "Error", required = false, nillable = false)
	public List<Error> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<Error>();
		}
		return errors;
	}

	public void setErrors(final List<Error> errors) {
		this.errors = errors;
	}

	public void addError(Error error) {
		getErrors().add(error);
	}

	private List<Error> errors = new ArrayList<Error>();

	@Override
	public String toString() {
		return "Errors [errors=" + errors + "]";
	}

}
