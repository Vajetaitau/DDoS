package ufc.utils;

import org.jnetpcap.Pcap;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.JHeader;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.annotate.Protocol;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;
import org.springframework.stereotype.Component;
import ufc.persistence.entity.Packet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;

@Component
public class FileUploader {

    public void uploadFile(String[] pathToFileArr) {


    }

//    public void uploadFile(String[] pathToFileArr) {
//        /***************************************************************************
//         * First we setup error buffer and name for our file
//         **************************************************************************/
//        final StringBuilder errbuf = new StringBuilder(); // For any error msgs
//        final String file = pathToFileArr[0];
//
//        System.out.printf("Opening file for reading: %s%n", file);
//
//        /***************************************************************************
//         * Second we open up the selected file using openOffline call
//         **************************************************************************/
//        Pcap pcap = Pcap.openOffline(file, errbuf);
//
//        if (pcap == null) {
//            System.err.printf("Error while opening device for capture: " + errbuf.toString());
//            return;
//        }
//
//        int i = 0;
//
//        /***************************************************************************
//         * Third we create a packet handler which will receive packets from the
//         * libpcap loop.
//         **************************************************************************/
//        PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {
//
//            public void nextPacket(PcapPacket packet, String user) {
//                int size = packet.size();
//                JBuffer buffer = packet;
//
//                byte[] arr = packet.getByteArray(0, size);
//
//
//                Tcp tcp = new Tcp();
//                Ip4 ip = new Ip4();
//                Udp udp = new Udp();
//                Http http = new Http();
//
//
//
//                byte[] sIP = new byte[4];
//                byte[] dIP = new byte[4];
//                String sourceIP = "";
//                String destIP = "";
//                if (packet.hasHeader(ip) && packet.hasHeader(tcp)) {
//                    sIP = packet.getHeader(ip).source();
//                    sourceIP = org.jnetpcap.packet.format.FormatUtils.ip(sIP);
//                    dIP = packet.getHeader(ip).destination();
//                    destIP = org.jnetpcap.packet.format.FormatUtils.ip(dIP);
//                    System.out.println("*" + sourceIP + "*" + destIP);
//                } else {
//                    System.out.println("ERROR");
//                }
//
//                System.out.printf("Received at %s caplen=%-4d len=%-4d %s\n",
//                        new Date(packet.getCaptureHeader().timestampInMillis()),
//                        packet.getCaptureHeader().caplen(), // Length actually captured
//                        packet.getCaptureHeader().wirelen(), // Original length
//                        user // User supplied object
//                );
//            }
//        };
//
//        /***************************************************************************
//         * Fourth we enter the loop and tell it to capture 10 packets. The loop
//         * method does a mapping of pcap.datalink() DLT value to JProtocol ID, which
//         * is needed by JScanner. The scanner scans the packet buffer and decodes
//         * the headers. The mapping is done automatically, although a variation on
//         * the loop method exists that allows the programmer to sepecify exactly
//         * which protocol ID to use as the data link type for this pcap interface.
//         **************************************************************************/
//        try {
//            pcap.loop(1000, jpacketHandler, "jNetPcap rocks!");
//        } finally {
//            /***************************************************************************
//             * Last thing to do is close the pcap handle
//             **************************************************************************/
//            pcap.close();
//        }
//    }

}
