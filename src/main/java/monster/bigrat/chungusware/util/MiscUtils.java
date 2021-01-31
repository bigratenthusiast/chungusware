package monster.bigrat.chungusware.util;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vector3d;

public class MiscUtils {
    public static Vector3d vec3FromYawVel(float yaw, float vel) {
        Vector3d vec = new Vector3d();
        vec.x = Math.cos(yaw) * vel;
        vec.z = Math.sin(yaw) * vel;
        return vec;
    }

    public static Vector3d movementInputToVelocity(Vector3d movementInput, float speed, float yaw) {
        double d = lengthSquared(movementInput);
        if (d < 1.0E-7D) {
            return new Vector3d();
        } else {
            Vector3d vec3d = multiply((d > 1.0D ? normalize(movementInput) : movementInput), speed);
            float f = MathHelper.sin(yaw * 0.017453292F);
            float g = MathHelper.cos(yaw * 0.017453292F);
            Vector3d j = new Vector3d();
            j.x = vec3d.x * (double) g - vec3d.z * (double) f;
            j.y = vec3d.y;
            j.z = vec3d.z * (double) g + vec3d.x * (double) f;
            return j;
        }
    }

    public static double lengthSquared(Vector3d v) {
        return (v.x * v.x) + (v.y * v.y) + (v.z * v.z);
    }

    public static Vector3d normalize(Vector3d t) {
        double d = MathHelper.sqrt_double(lengthSquared(t));
        Vector3d l = new Vector3d();
        if (d > 1.0E-4D) {
            l.x = t.x / d;
            l.y = t.y / d;
            l.z = t.z / d;
        }
        return l;
    }

    public static Vector3d multiply(Vector3d a, double b) {
        a.x = a.x * b;
        a.y = a.y * b;
        a.z = a.z * b;
        return a;
    }

    public static Vector3d add(Vector3d a, Vector3d b) {
        Vector3d c = new Vector3d();
        c.x = a.x + b.x;
        c.y = a.y + b.y;
        c.z = a.z + b.z;
        return c;
    }
}
