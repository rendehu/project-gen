package ${groupId}.base.concurrent;

/**
 * @author ${author}
 * @since 2021-12-22 17:59:10
 */
public abstract class Task extends Thread {

    public Task(){

    }

    /**
     *
     * 重写 Runnable 接口
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        before();
        doRun();
        after();
    }

    /**
     * Before action
     */
    protected void before(){

    }

    /**
     * After action
     */
    protected  void after(){}

    /**
     * Run detail like  Runnable.run()
     */
    protected abstract void doRun();



}
