package board.board.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_board_comment")
@NoArgsConstructor
@Data
public class BoardCommentEntity {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int commentIdx;
	
	@Column(nullable=false)
	@Lob
	private String contents;
	
	@Column(nullable=false, columnDefinition = "varchar(255) default 'N'")
	private String deletedYn;
	
	@Column(nullable=false)
	private String creatorId;
	
	@Column(nullable=false)
	private LocalDateTime createdDatetime = LocalDateTime.now();
	
	private LocalDateTime updatedDatetime;
	
	
}
