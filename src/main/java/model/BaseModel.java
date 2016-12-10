package model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

//@MappedSuperclass diz que informações de mapeamento sao aplicadas as entidades
//filhas (classes que estendem BaseModel).
@MappedSuperclass
public abstract class BaseModel implements Serializable {
	
	/**Codigo do objeto */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE")
	protected Long objectID;

	protected BaseModel() {
            objectID = new Long(-1);
	}

	public void setObjectID(String objectID) {
            this.objectID = Long.valueOf(objectID);
	}
	
	public void setObjectID(Long objectID) {
            this.objectID = objectID;
	}

	public Long getObjectID() {
            return objectID;
	}

        /*
         * Metods hashCode() e equals() precisam ser implementados se:
         * pretende-se inserir instâncias de classes persistentes em um Set, e
         * pretende-se usar reconexão de instâncias desanexadas.
         * Mais detalhes na secao 4.3 da referencia do hibernate.
         */
        
	/**Retorna o codigo hash da propriedade <code>objectID</code>. *
	 * @return int codigo hash da propriedade <code>objectID</code>.*/
	public int hashCode() {
            return objectID.hashCode();
	}

	/**Retorna <code>true</code> se <code>obj</code> for um
	 * <code>BaseModel</code> e se a propriedade <code>objectID</code> de
	 * ambos forem iguais.
	 * @param obj
	 *            referencia par ser comparada
	 * @return boolean <code>true</code> se objectID de <code>obj</code> for
	 *         igual ao deste objeto.*/
	public boolean equals(Object obj) {
            try {
                return this.getClass().equals(obj.getClass()) && objectID.equals(((BaseModel) obj).objectID);
            } catch (Exception ex) {
                return false;
            }
	}
	
	/**Retorna a String que repesenta a propriedade <code>objectID</code>. 
	 * @return String que repesenta a propriedade <code>objectID</code>.*/
	public String toString() {
            return this.objectID.toString();
	}

	public boolean isNew() {
            return objectID <= 0;
	}
        
}