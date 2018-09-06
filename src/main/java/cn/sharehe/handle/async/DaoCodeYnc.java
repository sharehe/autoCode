package cn.sharehe.handle.async;
import cn.sharehe.handle.configure.ClassNameConfigure;

/**
 * 生成数据库操作接口
 */
public class DaoCodeYnc extends BaseJavaCode implements Runnable {
    
    public DaoCodeYnc(String className){
        super(className);
    }
    public void run() {
        code();
    }
    private void code(){
        buf = appendPackage(packageNameConfigure.getDao());
        buf.append("import org.springframework.stereotype.Repository;\n");
        buf.append("\n\t/**\n\t*数据库操作接口\n\t*"+className+"表"+"\n\t*/\n");
        buf.append("\n@Repository\n");
        super.code(removeJava(ClassNameConfigure.className.get(ClassNameConfigure.DAO)),false,true);
        createFile(packageNameConfigure.getDao(), codeReplace( ClassNameConfigure.className.get(ClassNameConfigure.DAO)));
    }
}
