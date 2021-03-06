import Colliders.Collision;
import Components.AngularDynamics;
import Components.Component;
import Components.LinearDynamics;
import Colliders.Rigidbody;
import EngineCore.GameObject;
import InputInterface.Input;
import InputInterface.KeyCode;
import ScarMath.Vector3D;

public class PlayerControl extends Component {
    int linearAccelerationValue;
    double angularVelocityValue;

    Vector3D objectLinearAcceleration;
    AngularDynamics angularDynamics;
    Rigidbody rigidBody;

    @Override
    public void awake() {
        linearAccelerationValue = 3;
        angularVelocityValue    = 0.2f;

        objectLinearAcceleration = gameObject.getComponent(LinearDynamics.class).acceleration;
        angularDynamics = gameObject.getComponent(AngularDynamics.class);
        rigidBody       = gameObject.getComponent(Rigidbody.class);
    }

    @Override
    public void update(double dt) {
        manageWSAD();
        manageRotation();
        collision();
    }

    private void collision() {
        for(Collision collision : rigidBody.collisionList)
            if (collision != null)
                if (collision.collided) {
                    GameObject obiektKolizji = collision.A;
                    if (obiektKolizji == gameObject)
                        obiektKolizji = collision.B;

                    System.out.println("Detected collision with object [" + obiektKolizji.name +"]");
                }
    }

    private void manageWSAD() {
        if(Input.getKeyDown(KeyCode.W))
            objectLinearAcceleration.add(new Vector3D(0, -linearAccelerationValue));
        if(Input.getKeyReleased(KeyCode.W))
            objectLinearAcceleration.add(new Vector3D(0, linearAccelerationValue));

        if(Input.getKeyDown(KeyCode.S))
            objectLinearAcceleration.add(new Vector3D(0, linearAccelerationValue));
        if(Input.getKeyReleased(KeyCode.S))
            objectLinearAcceleration.add(new Vector3D(0, -linearAccelerationValue));

        if(Input.getKeyDown(KeyCode.A))
            objectLinearAcceleration.add(new Vector3D(-linearAccelerationValue, 0));
        if(Input.getKeyReleased(KeyCode.A))
            objectLinearAcceleration.add(new Vector3D(linearAccelerationValue, 0));

        if(Input.getKeyDown(KeyCode.D))
            objectLinearAcceleration.add(new Vector3D(linearAccelerationValue, 0));
        if(Input.getKeyReleased(KeyCode.D))
            objectLinearAcceleration.add(new Vector3D(-linearAccelerationValue, 0));
    }

    private void manageRotation() {
        if(Input.getKeyDown(KeyCode.Q))
            angularDynamics.angularVelocity -= angularVelocityValue;
        if(Input.getKeyReleased(KeyCode.Q))
            angularDynamics.angularVelocity += angularVelocityValue;

        if(Input.getKeyDown(KeyCode.E))
            angularDynamics.angularVelocity += angularVelocityValue;
        if(Input.getKeyReleased(KeyCode.E))
            angularDynamics.angularVelocity -= angularVelocityValue;
    }

}
