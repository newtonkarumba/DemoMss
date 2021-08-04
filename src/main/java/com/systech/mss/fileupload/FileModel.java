package com.systech.mss.fileupload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

@Setter
@Getter
@ToString
@JsonIgnoreProperties({"json"})
public class FileModel {
    String fileName;
    String originalFileName;
    String filePath;
    long size;

    @Ignore
    String comments;

    public static FileModel from(JSONObject jsonObject) {
        try {
            FileModel fileModel = new FileModel();
            fileModel.setFileName(jsonObject.getString("fileName"));
            fileModel.setOriginalFileName(jsonObject.getString("originalFileName"));
            fileModel.setFilePath(jsonObject.getString("filePath"));
            fileModel.setSize(jsonObject.getLong("size"));
            if (jsonObject.has("comments"))
                fileModel.setComments(jsonObject.getString("comments"));
            return fileModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fileName", getFileName());
            jsonObject.put("originalFileName", getOriginalFileName());
            jsonObject.put("filePath", getFilePath());
            jsonObject.put("size", getSize());
            jsonObject.put("comments", getComments());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
