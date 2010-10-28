package javax.microedition.io;

/**
 * Valid URI scheme:
 * <pre>
 * Connector.open("comm:/dev/ttyS0;baudrate=19200;bitsperchar=8;stopbits=1;parity=none;blocking=on;autocts=on;autorts=on");
 * </pre>
 */
public interface CommConnection extends StreamConnection {
    int getBaudrate();
    int setBaudrate(int baudrate);
}
