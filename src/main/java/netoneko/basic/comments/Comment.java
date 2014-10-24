package netoneko.basic.comments;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "comments")
public class Comment {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert=true)
    public Integer id;

    @DatabaseField(canBeNull = false)
    public String text;

    public Comment() {

    }

    public Comment(String text) {
        this.text = text;
    }
}
