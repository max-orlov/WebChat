package dataBase;

        import org.hibernate.Criteria;
        import org.hibernate.Session;
        import org.hibernate.criterion.Restrictions;

        import java.util.ArrayList;
        import java.util.List;

public class DAO {
    private static Session openedSession;

    public static List getAllObjects(Class className){
        openedSession = HibernateUtil.getSession();
        return openedSession.createCriteria(className).list();
    }

    public static void addObject(Object o){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
    }

    public static void addObjects(ArrayList<?> objects) {
        for (int i = 0; i < objects.size(); i++) {
            addObject(objects.get(i));
        }
    }

    public static Object getObjectById(Long id, Class className){
        openedSession = HibernateUtil.getSession();
        return openedSession.get(className, id);
    }

    public static Object getObjectByParam(String prmName, Object dataPrm, Class className){
        openedSession = HibernateUtil.getSession();
        openedSession.beginTransaction();
        Object o = openedSession.createCriteria(className)
                .add(Restrictions.eq(prmName, dataPrm)).uniqueResult();
        openedSession.getTransaction().commit();
        return o;
    }

    public static Object getObjectByParams(String[] prms, Object[] dataPrms, Class className){
        openedSession = HibernateUtil.getSession();
        openedSession.beginTransaction();
        Criteria criteria = openedSession.createCriteria(className);
        for (int i = 0; i < prms.length; i++)
            criteria.add(Restrictions.eq(prms[i], dataPrms[i]));
        Object o = criteria.uniqueResult();
        openedSession.getTransaction().commit();
        return o;
    }

    public static List getObjectsByParams(String[] prms, Object[] dataPrms, Class className){
        openedSession = HibernateUtil.getSession();
        openedSession.beginTransaction();
        Criteria criteria = openedSession.createCriteria(className);
        for (int i = 0; i < prms.length; i++)
            criteria.add(Restrictions.eq(prms[i], dataPrms[i]));
        List o = criteria.list();
        openedSession.getTransaction().commit();
        return o;
    }

    public static List getObjectsByParam(String prmName, Object dataPrm, Class className){
        openedSession = HibernateUtil.getSession();
        openedSession.beginTransaction();
        List o = openedSession.createCriteria(className)
                .add(Restrictions.eq(prmName, dataPrm)).list();
        openedSession.getTransaction().commit();
        return o;
    }

    public static void deleteObject(Long id, Class className) {
        Object objectById = getObjectById(id, className);
        openedSession.beginTransaction();
        openedSession.delete(objectById);
        openedSession.getTransaction().commit();
        closeOpenedSession();
    }

    public static void deleteObject(Object o){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        closeOpenedSession();
    }

    public static List executeSQLQuery(String SQLQuery){
        openedSession = HibernateUtil.getSession();
        List list = openedSession.createSQLQuery(SQLQuery).list();
        return list;
    }

    public static void closeOpenedSession(){
        HibernateUtil.closeSession(openedSession);
    }
}
