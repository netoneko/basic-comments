package netoneko.basic.comments;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

@DatabaseTable(tableName = "comments")
public class Comment {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert=true)
    public Integer id;

    @DatabaseField(canBeNull = false)
    public String text;

    @DatabaseField(canBeNull = false)
    public Timestamp createdAt;

    public Comment() {

    }

    public Comment(String text) {
        this.text = text;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
