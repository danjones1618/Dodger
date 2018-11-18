package com.alphamars.dodger.Graphics;

import android.opengl.GLES20;

import com.alphamars.dodger.Maths.Vector3f;

import java.util.HashMap;

public class Shader {

    public static Shader modelShader = new Shader(
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vecPos;" +
                    "uniform vec3 pos;" +
                    "varying vec3 col;" +
                    "void main(){" +
                    "    gl_Position = uMVPMatrix * (vecPos + vec4(pos, 0.0));" +
                    "    col = vecPos.xyz;" +
                    "}",
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "varying vec3 col;" +
                    "void main() {" +
                    "   gl_FragColor = vColor + vec4(col, 1.0);" +
                    "}");

    // TODO finish texturing
    public static Shader modelShaderTextured = new Shader(
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vecPos;" +
                    "uniform vec3 pos;" +
                    "varying vec2 UV;" +
                    "void main(){" +
                    "    gl_Position = uMVPMatrix * (vecPos + vec4(pos, 0.0));" +
                    "    UV = vecPos.xy;" +
                    "}",
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "varying vec2 UV;" +
                    "uniform sampler2D tex;" +
                    "void main() {" +
                    "   gl_FragColor = texture(tex, UV)" +
                    "}");

    private int shaderID;

    // <name, type)
    private HashMap<String, String> uniforms;

    public Shader(String vertexShaderCode, String fragmentShaderCode){
        int vsLocation = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fsLocation = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        shaderID = GLES20.glCreateProgram();
        GLES20.glAttachShader(shaderID, vsLocation);
        GLES20.glAttachShader(shaderID, fsLocation);
        GLES20.glLinkProgram(shaderID);

        findUniforms(vertexShaderCode);
        findUniforms(fragmentShaderCode);
    }

    private void findUniforms(String code){
        if (uniforms == null)
            uniforms = new HashMap<String, String>();

        int index = code.indexOf("uniform");
        int eol = code.indexOf(';', index);
        String line, type, name;
        while(index > -1 && eol > -1){
            line = code.substring(index + 7, eol);
            line = line.trim();
            type = line.substring(0, line.indexOf(' ')).trim();
            name = line.substring(line.indexOf(' ')).trim();
            uniforms.put(name, type);

            index = code.indexOf("uniform", eol);
            eol = code.indexOf(';', index);
        }
    }

    // Returns true on success
    //TODO implement all possible unfirom types + structs
    public <T> boolean updateUniform(String uniformName, T value){
        int uniformLocation = GLES20.glGetUniformLocation(shaderID, uniformName);

        switch(uniforms.get(uniformName)){
           case "vec3":
                if (value instanceof Vector3f)
                    GLES20.glUniform3fv(uniformLocation, ((Vector3f) value).toFloatArray().length/3, ((Vector3f) value).toFloatArray(), 0);
                else if(value instanceof float[] && ((float[]) value).length % 3 == 0)
                    GLES20.glUniform3fv(uniformLocation, ((float[]) value).length/3, (float[]) value, 0);
                else
                    return false;
                break;

            case "vec4":
                if (value instanceof float[] && ((float[]) value).length % 4 == 0)
                    GLES20.glUniform4fv(uniformLocation, ((float[]) value).length/4, (float[]) value, 0);
                else
                    return false;
                break;

            case "mat4":
                if (value instanceof float[] && ((float[]) value).length % 16 == 0)
                    GLES20.glUniformMatrix4fv(uniformLocation, ((float[]) value).length/ 16, false, (float[]) value, 0);
                else
                    return false;
                break;

            case "sampler2D":
                GLES20.glUniform1i(uniformLocation, (Integer) value);
            default:
                return false;
        }
        return true;
    }

    private int loadShader (int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    public int getShaderID(){
        return shaderID;
    }
}
