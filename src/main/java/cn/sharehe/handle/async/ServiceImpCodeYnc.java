package cn.sharehe.handle.async;
import cn.sharehe.handle.configure.ClassNameConfigure;

public class ServiceImpCodeYnc extends BaseJavaCode implements Runnable {
    public ServiceImpCodeYnc(String className) {
        super(className);
    }
    public void run() {
        code();
    }
    public void code(){
        buf= appendPackage(packageNameConfigure.getServiceImp());
        buf.append("import "+packageNameConfigure.getRootPackage()+packageNameConfigure.getDao()+"."+removeJava(ClassNameConfigure.className.get(ClassNameConfigure.DAO))+";\n");
        if(openConfigure.isService())
            buf.append("import "+packageNameConfigure.getRootPackage()+packageNameConfigure.getService()+"."+removeJava(ClassNameConfigure.className.get(ClassNameConfigure.SERVICE))+";\n");
        buf.append("import org.springframework.beans.factory.annotation.Autowired;\n");
        buf.append("import org.springframework.stereotype.Service;");
        buf.append("\n\t/**\n\t*service实现类\n\t*"+className+"服务"+"\n\t*/\n");
        buf.append("\n@Service\n");
        super.code(removeJava(ClassNameConfigure.className.get(ClassNameConfigure.SERVICEIMP)),true);
//        super.code(log,"ServiceImp");
        createFile(packageNameConfigure.getServiceImp(), codeReplace(ClassNameConfigure.className.get(ClassNameConfigure.SERVICEIMP)));
    }
}
