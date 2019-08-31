package ro.msg.edu.jbugs.managers.implementations;

import dao.CommentDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentManagerTest {

    @InjectMocks
    private CommentManager commentManager;

    @Mock
    private CommentDao commentDao;

    @Test
    public void deleteComments() {
        when(commentDao.deleteComments()).thenReturn(2);
        Assert.assertEquals((int) 2, (int) commentManager.deleteComments());
    }
}