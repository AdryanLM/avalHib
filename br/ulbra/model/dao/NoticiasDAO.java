/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ulbra.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import br.ulbra.connection.ConnectionFactory;
import br.ulbra.model.bean.Noticias;

public class NoticiasDAO {
     public void save(Noticias noticia) {
        EntityManager em = new ConnectionFactory().getConnection();
        try {
            em.getTransaction().begin();
            if (noticia.getId() == -1) {
                em.persist(noticia);
            } else { 
                em.merge(noticia);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public Noticias remove(Integer id) {
        EntityManager em = new ConnectionFactory().getConnection();
        Noticias noticia = null;
        try {
            noticia = em.find(Noticias.class, id);
            em.getTransaction().begin();
            
            em.remove(noticia);
            
            em.getTransaction().commit();
            JOptionPane.showMessageDialog(null, "removido");
        } catch (Exception e) {
            System.err.println(e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return noticia;
    }

    public List<Noticias> findAll() {
        EntityManager em = new ConnectionFactory().getConnection();
        List<Noticias> noticias = null;
        try {
            noticias = em.createQuery("from Noticias ns").getResultList();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return noticias;
    }
    
    public List<Noticias> findAllByFilter(String filter, String valor) {
        EntityManager em = new ConnectionFactory().getConnection();
        List<Noticias> noticias = null;
        try {
            noticias = em.createQuery("SELECT ns FROM Noticias ns WHERE ns." + filter + " LIKE '%" + valor + "%'")
                    .getResultList();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            em.close();
        }
        return noticias;
    }
}
