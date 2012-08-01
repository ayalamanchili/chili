package info.chili.service.jrs.types;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

@SuppressWarnings("restriction")
@XmlType
public class Error implements Serializable {

	private static final long serialVersionUID = 1L;

	public Error() {
		super();
	}

	public Error(String source, String reasonCode) {
		this.source = source;
		this.reasonCode = reasonCode;
	}

	public Error(String source, String reasonCode, String description) {
		this.source = source;
		this.reasonCode = reasonCode;
		this.description = description;
	}

	public Error(String source, String reasonCode, String key, String description) {
		this.source = source;
		this.reasonCode = reasonCode;
		this.description = description;
		this.key = key;
	}

	protected String source;
	protected String reasonCode;
	protected String key;
	protected String description;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Error [source=" + source + ", reasonCode=" + reasonCode + ", description=" + description + ", key="
				+ key + "]";
	}

}
