package com.ziscloud.zcdiagram.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Relation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "relation", catalog = "zcdiagram_db")
public class Relation implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -349207297388715196L;
	private Integer id;
	private DrawMeta drawMetaByCurAct;
	private DrawMeta drawMetaByPreAct;
	private Integer ordinal;

	// Constructors

	/** default constructor */
	public Relation() {
	}

	/** minimal constructor */
	public Relation(DrawMeta drawMetaByCurAct,
			DrawMeta drawMetaByPreAct) {
		this.drawMetaByCurAct = drawMetaByCurAct;
		this.drawMetaByPreAct = drawMetaByPreAct;
	}

	/** full constructor */
	public Relation(DrawMeta drawMetaByCurAct,
			DrawMeta drawMetaByPreAct, Integer ordinal) {
		this.drawMetaByCurAct = drawMetaByCurAct;
		this.drawMetaByPreAct = drawMetaByPreAct;
		this.ordinal = ordinal;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "curActivityId", nullable = false)
	public DrawMeta getDrawMetaByCurAct() {
		return this.drawMetaByCurAct;
	}

	public void setDrawMetaByCurAct(DrawMeta drawMetaByCurAct) {
		this.drawMetaByCurAct = drawMetaByCurAct;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "preActivityId", nullable = false)
	public DrawMeta getDrawMetaByPreAct() {
		return this.drawMetaByPreAct;
	}

	public void setDrawMetaByPreAct(DrawMeta drawMetaByPreAct) {
		this.drawMetaByPreAct = drawMetaByPreAct;
	}

	@Column(name = "ordinal")
	public Integer getOrdinal() {
		return this.ordinal;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Relation other = (Relation) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}