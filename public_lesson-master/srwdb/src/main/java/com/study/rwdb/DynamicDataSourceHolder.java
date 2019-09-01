package com.study.rwdb;

/**
 * 使用ThreadLocal技术来记录当前线程中的数据源的key
 * 
 * @author allen
 */
public final class DynamicDataSourceHolder {

	// 使用ThreadLocal记录当前线程的数据源key
    private static final ThreadLocal<DynamicDataSourceGlobal> holder = new ThreadLocal<DynamicDataSourceGlobal>();

    private DynamicDataSourceHolder() {
        // ......
    }

    /**
     * 设置数据源
     * 
     * @param dataSource
     */
    public static void putDataSource(DynamicDataSourceGlobal dataSource){
        holder.set(dataSource);
    }

    /**
     * 获取数据源
     * @return
     */
    public static DynamicDataSourceGlobal getDataSource(){
        return holder.get();
    }

    /**
     * 清理数据源
     */
    public static void clearDataSource() {
        holder.remove();
    }

}
