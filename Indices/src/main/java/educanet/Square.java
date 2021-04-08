package educanet;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Square {

    private float[] vertices;

    private final int[] indices = {
            0, 1, 3,
            1, 2, 3
    };
    private int squareVaoId;
    private int squareVboId;
    private int squareEboId;
    private int squareColorId;

    public Square(float x, float y, float size) {
        float longest = (float) Math.sqrt(25);

        float a1 = (float) Math.sqrt(((x + size) * (x + size)) + (y * y));
        float b1 = (float) Math.sqrt(((x + size) * (x + size)) + ((y - size) * (y - size)));
        float c1 = (float) Math.sqrt((x * x) + ((y - size) * (y - size)));
        float d1 = (float) Math.sqrt((x * x) + (y * y));

        float a2 = (a1 / longest);
        float b2 = (b1 / longest);
        float c2 = (c1 / longest);
        float d2 = (d1 / longest);

        float[] barvyPOOG = {
                (float) (a2 + 0.0), (float) (a2 + 0.0), a2, 69f,
                (float) (b2 + 0.0), (float) (b2 + 0.0), b2, 69f,
                (float) (c2 + 0.0), (float) (c2 + 0.0), c2, 69f,
                (float) (d2 + 0.0), (float) (d2 + 0.0), d2, 69f,
        };

        float[] vertices = {
                x + size, y, 0.0f,
                x + size, y - size, 0.0f,
                x, y - size, 0.0f,
                x, y, 0.0f,
        };

        this.vertices = vertices;

        squareVaoId = GL33.glGenVertexArrays();
        squareEboId = GL33.glGenBuffers();
        squareVboId = GL33.glGenBuffers();
        squareColorId = GL33.glGenBuffers();

        GL33.glBindVertexArray(squareVaoId);
        
        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, squareEboId);
        
        IntBuffer ib = BufferUtils.createIntBuffer(indices.length)
                .put(indices)
                .flip();
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareColorId);
        
        FloatBuffer cfb = BufferUtils.createFloatBuffer(barvyPOOG.length).put(barvyPOOG).flip();

        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cfb, GL33.GL_STATIC_DRAW);
        
        GL33.glVertexAttribPointer(1, 4, GL33.GL_FLOAT, false, 0, 0);
        
        GL33.glEnableVertexAttribArray(1);

        MemoryUtil.memFree(cfb);

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareVboId);
        
        FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                .put(vertices)
                .flip();
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);
        
        MemoryUtil.memFree(fb);
        
        MemoryUtil.memFree(ib);
    }

    public void render() {
        GL33.glUseProgram(educanet.Shaders.shaderProgramId);

        GL33.glBindVertexArray(squareVaoId);
        
        GL33.glDrawElements(GL33.GL_TRIANGLES, indices.length, GL33.GL_UNSIGNED_INT, 0);
    }
}