package net.marioosh.gwt.shared.model.entities;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "links")
@SequenceGenerator(name = "seq_link", sequenceName = "seq_link", allocationSize = 1)
public class Link extends AbstractEntity implements Serializable, IsSerializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_link")
	private Long id;

	@NotEmpty
	@Size(min=3)
	private String address;
	
	private String name;
	private String description;

	@NotNull
	@DateTimeFormat(pattern="dd.MM.yyyy")
	private Date date;

	public Link() {}

	public Link(String address, String name, String description) {
		super();
		this.address = address;
		this.name = name;
		this.description = description;
		date = new Date();
	}

	public Long getId() {
		return id;
	}

	
	public void setId(Long id) {
		this.id = id;
	}

	
	public String getAddress() {
		return address;
	}

	
	public void setAddress(String address) {
		this.address = address;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/*
	@Override
	public String toString() {
		final BeanWrapper wrapper = new BeanWrapperImpl(this);
		StringBuilder sb = new StringBuilder();
	    for(final PropertyDescriptor descriptor : wrapper.getPropertyDescriptors()){
	    	try {
				sb.append(descriptor.getName() + ":" + descriptor.getReadMethod().invoke(this));
			} catch (Exception e) {
			}
	    }		
	    return sb.toString();
	}
	*/
}
