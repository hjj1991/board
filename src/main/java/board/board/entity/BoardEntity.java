package board.board.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_board")
@NoArgsConstructor
@Data
public class BoardEntity {
	
	@Column(nullable=false)
	private int boardType = 0;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int boardIdx;
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false)
	@Lob
	private String contents;
	
	@Column(nullable=false, columnDefinition = "int(11) default 0")
	private int hitCnt = 0;
	
	@Column(nullable=false, columnDefinition = "varchar(255) default 'N'")
	private String deletedYn;
	
	@Column(nullable=false)
	private String creatorId;
	
	@Column(nullable=false)
	private LocalDateTime createdDatetime = LocalDateTime.now();
	
	private String updaterId;
	
	private LocalDateTime updatedDatetime;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="board_idx")
	private Collection<BoardFileEntity> fileList;
	
	@OneToMany(cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	@JoinColumn(name="board_idx")
	private List<BoardCommentEntity> commentList = new ArrayList<BoardCommentEntity>();
	
	
}
