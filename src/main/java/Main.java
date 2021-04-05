import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;


public class Main {

    public static void main(String[] args) throws Exception {
        GLFW.glfwInit();
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3); // OpenGL 3

        long window = GLFW.glfwCreateWindow(800, 600, "CTVEREC ALE JE TO OBDELNIK O_O", 0, 0);
        if (window == 0) {
            GLFW.glfwTerminate();
            throw new Exception("bum");
        }
        GLFW.glfwMakeContextCurrent(window);

        GL.createCapabilities();
        GL33.glViewport(0, 0, 800, 600);

        GLFW.glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            GL33.glViewport(0, 0, w, h);
        });

        Ctverec.init(window);

        while (!GLFW.glfwWindowShouldClose(window)) {
            //  background color
            GL33.glClearColor(0, 0, 0, 1f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
                GLFW.glfwSetWindowShouldClose(window, true);
            }

            Ctverec.render(window);
            Ctverec.update(window);


            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
        GLFW.glfwTerminate();
    }

}