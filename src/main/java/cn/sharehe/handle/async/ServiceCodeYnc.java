package cn.sharehe.handle.async;
import cn.sharehe.handle.configure.ClassNameConfigure;

public class ServiceCodeYnc extends BaseJavaCode implements Runnable {
    public ServiceCodeYnc(String className) {
        super(className);
    }

    private void code() {
        buf= appendPackage(packageNameConfigure.getService());
        buf.append("\n\t/**\n\t*service服务接口\n\t*"+className+"实体"+"\n\t*/\n");
        super.code(removeJava(ClassNameConfigure.className.get(ClassNameConfigure.SERVICE)),false);
        createFile(packageNameConfigure.getService(), codeReplace(ClassNameConfigure.className.get(ClassNameConfigure.SERVICE)));
    }

    public void run() {
        code();
    }
}
