package db;

public class People {
    public int ID = -1;
    public String Name;
    public String Banji;
    public String Xuehao;

    @Override
    public String toString() {
        String result = "";
        result += "ID：" + this.ID + "，";
        result += "姓名：" + this.Name + "，";
        result += "班级：" + this.Banji + "， ";
        result += "学号：" + this.Xuehao;
        return result;
    }
}


