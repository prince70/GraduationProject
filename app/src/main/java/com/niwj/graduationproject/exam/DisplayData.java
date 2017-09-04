package com.niwj.graduationproject.exam;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;

import com.gdhktech.wisdomdoc.exam.MyApplication;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import android_serialport_api.SerialPort;

public class DisplayData {
    private int i_thread_work = 1;
    private int n_start = 0; // ����ǰ�ж������Ƿ�׼����
    private int n_start2 = 0;
    private int i_start = 0; // �������
    private int i_start2 = 0;
    float YY_zoom = 0; // Y����
    float XX_zoom = 0;
    float Y_zoom = 0; // Y����
    float X_zoom = 0;
    private int i_stop = 0;
    private int temp_osld = 0;

    int value7_10[] = new int[] { 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1 };
    int[] _Buffer = new int[20]; // ��������
    int _Task = 0;
    int _Length = 0;
    int _Out = 0;

    private int i_serial_data = 0;

    private int i_hr_high = 100;
    private int i_hr_low = 60;
    private int i_spo2_high = 100;
    private int i_spo2_low = 94;
    private int i_pr_high = 100;
    private int i_pr_low = 60;

    private static int _PackageLengthMin = 4; // ID_ECGWAVE~ID_ARR�İ��е���С����Ϊ4
    private static int ePackIdCmdEnd = 0x65;
    private static int ID_ECGWAVE = 0x05;

    RQueue _queue = new RQueue(200000);
    RQueue _queue_point = new RQueue(100000);
    RQueue _queue_point2 = new RQueue(100000);
    RQueue _queue_point_PM = new RQueue(100000);

    // Ѫѹ
    int i_press = 0;

    // �ĵ�
    int ecg_count = 22; // ��ͼ��
    int[] i_point = new int[ecg_count]; // ������10������
    int[] i_point_PM = new int[ecg_count]; // ���ź�

    // Ѫ��
    int n_Total_count = 30;
    int[] i_point2 = new int[n_Total_count + 1]; // ������10������

    int glob_heartrate = 0;
    int glob_st1 = 1;
    int glob_st2 = 2;
    int glob_osld = 3;
    int glob_pulserate = 4;
    int glob_systolic = 5;
    int glob_diastolic = 6;
    int glob_avg = 7;
    int glob_xiudai = 8;
    int glob_INVALUE = -10000;

    private int[] i_value = new int[] { glob_INVALUE, glob_INVALUE, glob_INVALUE, glob_INVALUE, glob_INVALUE,
            glob_INVALUE, glob_INVALUE, glob_INVALUE, glob_INVALUE };

    // 包类型ID对应的长度
    int[] cPackLengthTable = new int[] { 0, // 00: 保留
            2, // 01: 系统复位信息
            5, // 02: 系统状态
            9, // 03: 系统自检
            5, // 04: 命令应答
            8, // 05: ECG波形数据
            5, // 06: 导联连接和信号信息
            5, // 07: 心率
            0, // 08: 保留
            4, // 09: PVCS室性早搏
            6, // 0a: ARR
            7, // 0b: ST偏移
            9, // 0c: ST段波形值
            0, // 0d: 保留
            0, // 0e: 保留
            0, // 0f: 保留
            4, // 10: 呼吸波
            5, // 11: 呼吸率
            6, // 12: 窒息报警
            4, // 13: CVA报警信息
            0, // 14: 保留
            8, // 15: 体温数据
            5, // 16: SPO2波形数据
            7, // 17: SPO2数据
            7, // 18: IBP波形数据
            4, // 19: IBP状态
            10, // 1a: IBP压力
            6, // 1b: IBP校零和校准信息
            7, // 1c: IBP校零和校准时间
            0, // 1d: 保留
            0, // 1e: 保留
            0, // 1f: 保留
            7, // 20: NBP实时测量数据
            4, // 21: NBP测量结束包
            9, // 22: NBP测量结果1
            5, // 23: NBP测量结果2
            8, // 24: NBP测量状态
            0, // 25: 保留
            0, // 26: 保留
            0, // 27: 保留
            0, // 28: 保留
            0, // 29: 保留
            0, // 2a: 保留
            0, // 2b: 保留
            0, // 2c: 保留
            0, // 2d: 保留
            0, // 2e: 保留
            0, // 2f: 保留
            0, // 30: 保留
            0, // 31: 保留
            0, // 32: 保留
            0, // 33: 保留
            0, // 34: 保留
            0, // 35: 保留
            0, // 36: 保留
            0, // 37: 保留
            0, // 38: 保留
            0, // 39: 保留
            0, // 3a: 保留
            0, // 3b: 保留
            0, // 3c: 保留
            0, // 3d: 保留
            0, // 3e: 保留
            0, // 3f: 保留
            2, // 40: 读取自检结果
            4, // 41: 接收数据选择
            4, // 42: 病人信息设置
            0, // 43: 保留
            0, // 44: 保留
            4, // 45: 导联模式选择:3,5导
            4, // 46: 导联方式选择
            4, // 47: 滤波方式选择
            4, // 48: 心电增益
            4, // 49: 1mV校准信号设置
            4, // 4a: 工频抑制设置
            4, // 4b: 起搏分析开关设置
            7, // 4c: ST测量的ISO和ST点
            4, // 4d: 设置心电分析通道
            2, // 4e: 心电自学习触发
            0, // 4f: 保留
            4, // 50: 呼吸增益
            0, // 51: 保留
            4, // 52: 窒息报警时间选择
            4, // 53: 体温探头类型设置
            4, // 54: SPO2设置
            2, // 55: NBP开始一次手动/自动测量
            2, // 56: NBP结束测量
            4, // 57: NBP测量周期设置
            2, // 58: NBP开始校准
            2, // 59: NBP复位
            2, // 5a: NBP漏气检测
            2, // 5b: NBP查询状态
            5, // 5c: NBP设置初次充气压力
            2, // 5d: NBP开始STAT测量
            2, // 5e: NBP查询结果
            0, // 5f: 保留
            4, // 60: IBP设置压力名称
            4, // 61: IBP校零
            7, // 62: IBP1校准
            7, // 63: IBP2校准
            4, // 64: 设置数字滤波模式
            5, // 65: IBP查询
            0, // 66: 保留
            0, // 67: 保留
            0, // 68: 保留
            0, // 69: 保留
            0, // 6a: 保留
            0, // 6b: 保留
            0, // 6c: 保留
            0, // 6d: 保留
            0, // 6e: 保留
            0, // 6f: 保留
            0, // 70: 保留
            0, // 71: 保留
            0, // 72: 保留
            0, // 73: 保留
            0, // 74: 保留
            0, // 75: 保留
            0, // 76: 保留
            0, // 77: 保留
            0, // 78: 保留
            0, // 79: 保留
            0, // 7a: 保留
            0, // 7b: 保留
            0, // 7c: 保留
            0, // 7d: 保留
            0, // 7e: 保留
            0, // 7f: 保留
    };


    private MyApplication mApplication;
    private SerialPort mSerialPort;
    protected OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;


    public DisplayData(){
    };

    public DisplayData(Context context){
        mApplication =  (MyApplication)context.getApplicationContext();
        try {
            if (_queue_point.Count() > 0) {
                _queue_point.Empty();
            }
            if (_queue_point_PM.Count() > 0) {
                _queue_point_PM.Empty();
            }
            if (_queue_point2.Count() > 0) {
                _queue_point2.Empty();
            }
            if (_queue.Count() > 0) {
                _queue.Empty();
            }

            mSerialPort = mApplication.getSerialPort();
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();

            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (Exception e) {

        }

    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x24:
                    Bundle bundle_0x24 = msg.getData();
                    int value = bundle_0x24.getInt("over_press");
                    i_value[glob_xiudai] = glob_INVALUE;
                    if (value == 0x24) {
                        fun_stop();
                    }
                    break;
                default:
                    break;
            }

        }
    };

    // ������д�����߳�
    public void ioPortData() {
        new ReadThread().start();
    }

    // �����߳�
    class ReadThread extends Thread {
        byte[] buffer = new byte[30000];

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {

                int size;
                try {

                    if (mInputStream == null)
                        return;
                    size = mInputStream.read(buffer);
                    while (size > 0) {
                        onDataReceived(buffer, size);
                        size = mInputStream.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }


    // �������ݻ�ָ��
    public void SendData(byte[] by_data, int length) {
        if (mOutputStream != null) {
            by_data[1] = (byte) ((byte) (by_data[0]) | (byte) 0x80);
        }

        try {
            mOutputStream.write(by_data);

            if (mOutputStream == null) {
                return;
            }

        } catch (Exception e) {
            // Toast.makeText(this, "����ָ��ʧ��!", Toast.LENGTH_SHORT).show();
        }
    }
    // ����
    public void SetupCmd(int cmd, int data) {
        int Sum = cmd;
        if (mOutputStream != null) {

            try {
                mOutputStream.write((byte) cmd);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if ((data & 0x80) != 0) {
                Sum += 0x81;
                try {
                    mOutputStream.write((byte) (0x81));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    mOutputStream.write((byte) (data));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                data |= 0x80;
                Sum += 0x80;
                try {
                    mOutputStream.write((byte) (0x80));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    mOutputStream.write((byte) (data));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            Sum += data;
            try {
                mOutputStream.write((byte) (Sum | 0x80));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void SetupCmd(int cmd) {
        if (mOutputStream != null) {
            try {
                mOutputStream.write((byte) cmd);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                mOutputStream.write((byte) (cmd | 0x80));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    // �Զ�����������
    public void auto_reset() {
        byte[] by_data = new byte[2];
        by_data[0] = (byte) 0x56;
        by_data[1] = (byte) 0x01;
        if (mOutputStream != null) {
            SendData(by_data, 1);
        }
    }

    // �������ݵ��߳�
    class SendData implements Runnable {

        int n_count = 1;
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (!Thread.currentThread().isInterrupted() && n_count > 0) {
                byte[] by_data = new byte[2];
                SendData(by_data, 1);
                n_count--;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    public void setupThread(){
        new Thread(new myTimer1()).start();
    }

    // ��ʱ����Ѫ������
    class myTimer1 implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (!Thread.currentThread().isInterrupted() && i_thread_work == 1) {

                // �����𲫼�⿪��
                SetupCmd(0x4b, 0x01);

                if (i_serial_data != i_stop) {
                    i_serial_data = i_stop;
                } else {
                }

                if (mOutputStream != null) {
                    // �ĵ�Ѫ���л�
                    SetupCmd(0x67, 0);
                    int var_zhenyi = 13;

                    int var_jianhu = 22;
                    int var_xianbo = 32;
                    int var_daolian = 42;
                    // ����
                    switch (var_zhenyi) {
                        case 13:
                            SetupCmd(0x48, 2);
                            SetupCmd(0x48, (1 << 4) | 3);
                            break;
                        default:
                            break;
                    }
                    // �໤
                    switch (var_jianhu) {
                        case 22:
                            SetupCmd(0x47, 1); // 0:��ϣ�1���໤��2������
                            break;

                    }
                    // �ݲ�
                    switch (var_xianbo) {
                        case 32:
                            SetupCmd(0x4A, 1); // �ݲ���
                            break;
                    }
                    // ����
                    switch (var_daolian) {

                        case 42:
                            SetupCmd(0x45, 0x00); // �л�
                            SetupCmd(0x46, 0x03); // ����2
                            break;
                    }
                } else {
                    return;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public void setupDataDeal(){
        new Thread(new DealData()).start();
    }

    // �������ݵ��߳�
    class DealData implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (!Thread.currentThread().isInterrupted() && i_thread_work == 1) {
                while (fun_DealData()) {
                    switch (_Buffer[0]) {
                        case 0x05: // �ĵ�
                            int a = 0;
                            a = ((_Buffer[1] & 0xff) * 256) + (_Buffer[2] & 0xff);
                            _queue_point.Push(a);
                            if ((_Buffer[5] & 0x02) == 0x02) {
                                _queue_point.Push(0xffff);
                            }
                            break;
                        case 0x07: // ����
                            int heart_rate = ((_Buffer[1] & 0xff) * 256) + (_Buffer[2] & 0xff);

                            if (heart_rate > 0 && heart_rate < 300) {
                                // �������޷�Χ�ڲ�����
                                if (heart_rate > 0) {
                                    i_value[glob_heartrate] = heart_rate;
                                } else {
                                    i_value[glob_heartrate] = glob_INVALUE;
                                }

                            } else {
                                i_value[glob_heartrate] = glob_INVALUE; // ��Чֵ
                            }
                            break;
                        case 0x0a:
                            int asy = _Buffer[1];

                            break;

                        case 0x16:
                            int b = 0;
                            b = (_Buffer[1] & 0xff); // Ѫ������
                            _queue_point2.Push(b);

                            break;
                        case 0x17: // �����Ͷ���Ϣ�����ʣ������Ͷ�����
                            int value2 = ((_Buffer[2] & 0xff) * 256) + (_Buffer[3] & 0xff); // ����
                            int value3 = (_Buffer[4] & 0xff); // �����Ͷ�

                            if (value3 < 200 && value2 < 300) {
                                // ��������
                                if ((value2 > 0) && (value2 > i_pr_high)) // ��������
                                {
                                    i_value[glob_pulserate] = glob_INVALUE;

                                }
                                if ((value2 > 0) && (value2 < i_pr_low) )// ��������
                                {
                                    i_value[glob_pulserate] = glob_INVALUE;
                                }
                                i_value[glob_pulserate] = value2; // ����
                                // ���浽���ݿ�

                                if ((value3 > 0) && (value3 > i_spo2_high) ) { // �����Ͷ�����
                                    i_value[glob_osld] = glob_INVALUE;
                                }
                                // Ѫ������
                                if ((value3 > 0) && (value3 < i_spo2_low)) // Ѫ������
                                {
                                    i_value[glob_osld] = glob_INVALUE;
                                }
                                i_value[glob_osld] = value3;

                            } else {
                                i_value[glob_osld] = glob_INVALUE;
                                i_value[glob_pulserate] = glob_INVALUE;
                            }
                            break;
                        case 0x20: // ���ѹ��
                            int xiu_dai = (_Buffer[1] & 0xff) * 256 + (_Buffer[2] & 0xff);
                            if (xiu_dai > 0 && xiu_dai < 300) {
                                i_value[glob_xiudai] = xiu_dai;

                            } else {
                                i_value[glob_xiudai] = glob_INVALUE;
                            }
                            break;
                        case 0x21:
                            SetupCmd(0x5b);
                            SetupCmd(0x5b);
                            break;
                        case 0x22: // ����ѹ������ѹ��ƽ��ѹ

                            int BP_value1 = (_Buffer[1] & 0xff) * 256 + (_Buffer[2] & 0xff);
                            int BP_value2 = ((_Buffer[3] & 0xff) * 256) + (_Buffer[4] & 0xff);
                            int BP_value3 = ((_Buffer[5] & 0xff) * 256) + (_Buffer[6] & 0xff);

                            if ((BP_value1 > 300) || (BP_value2 > 300) || (BP_value3 > 300)) {

                                i_value[glob_systolic] = glob_INVALUE;
                                i_value[glob_diastolic] = glob_INVALUE;
                                i_value[glob_avg] = glob_INVALUE;
                            } else {
                                if ((BP_value1 > 0) && (BP_value2 > 0) && (BP_value3 > 0)) {
                                    i_value[glob_systolic] = BP_value1;
                                    i_value[glob_diastolic] = BP_value2;
                                    i_value[glob_avg] = BP_value3;
                                    i_value[glob_xiudai] = glob_INVALUE; // �����Чֵ
                                } else {
                                    i_value[glob_systolic] = glob_INVALUE;
                                    i_value[glob_diastolic] = glob_INVALUE;
                                    i_value[glob_avg] = glob_INVALUE;
                                    // �����Чֵ
                                }

                                Message message_0x22 = new Message();

                                message_0x22.what = 0x22;
                                handler.sendMessage(message_0x22);
                            }
                            break;
                        case 0x24:
                            int over_pree = _Buffer[3];
                            // ��ѹ����
                            if (over_pree == 7) // ��ѹ����
                            {
                                Message message_0x24 = new Message();
                                Bundle bundle_0x24 = new Bundle();
                                message_0x24.what = 0x24;
                                bundle_0x24.putInt("over_press", 0x24);
                                message_0x24.setData(bundle_0x24);
                                handler.sendMessage(message_0x24);
                            }
                            break;
                        default:
                            break;
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    public boolean fun_DealData() {

        boolean bLoop = true;

        while (bLoop) // ���ڲ������ⲿ����֮ǰ�İ��Ѿ��������
        {
            // ��ʱѪ��
            // Send_SPOH2();

            switch (_Task) {
                case 0:
                    if (_queue.Count() >= _PackageLengthMin) {
                        // ��������Ч�ԣ���һ���ض���BIT7=0������ض�BIT7=1
                        int x = _queue.ReadByte(0);
                        if ((x < (int) ID_ECGWAVE) || (x >= (int) ePackIdCmdEnd)) {
                            // ����
                            _queue.PopByte();
                            break;
                        }

                        // ���յ�ָ������
                        x = _Buffer[0] = _queue.PopByte();
                        _Length = cPackLengthTable[x];
                        _Task++;
                    } else {
                        bLoop = false;
                    }
                    break;

                case 1:
                    if (_queue.Count() >= _Length) {
                        // ���㹻���ݣ������������У�����Ч��
                        int x;
                        for (int i = 0; i < _Length - 1; i++) {
                            x = _queue.ReadByte(i);

                            x = x & 0x80;
                            if (x == 0) {
                                // ����
                                _queue.PopByte();
                                _Task = 0;
                                break;
                            }
                        }

                        // ���У���
                        byte chksum = (byte) _Buffer[0];
                        for (int i = 0; i < _Length - 2; i++) {
                            chksum += _queue.ReadByte(i);
                        }
                        chksum |= 0x80;
                        x = _queue.ReadByte(_Length - 2);
                        if ((byte) x != chksum) {
                            // ����
                            _queue.PopByte();
                            _Task = 0;
                            break;
                        }

                        // ����ͷ�ֽڻ�ԭ��������
                        int Logic = 0x1;
                        int DataHead = _queue.PopByte();
                        for (int i = 2; i < _Length; i++) {
                            x = _queue.PopByte() & 0xff;
                            if ((DataHead & Logic) != 0) {
                                x |= 0x80;
                            } else {
                                x &= ~0x80;
                            }
                            _Buffer[i - 1] = x;
                            Logic <<= 1;
                        }
                        _Out = _Length;
                        _Task = 0;

                        return true; // ���а�������
                    } else {
                        bLoop = false;
                    }
                    break;
            }
        }

        return false;
    }

    public void fun_zoom(float x_zoom, float y_zoom) {
        Y_zoom = y_zoom;
        X_zoom = x_zoom;
    }

//    /***************** 2015-12-04 ͣ�����ı䲨�� ***********************/
//    // �ĵ�
//    public void get_data() {
//        if (_queue_point.Count() > 48) {
//            for (int j = 1; j < ecg_count; j++) {
//                _queue_point.PopByte();
//
//                if (_queue_point.ReadByte(0) != 0xffff)
//                // if (_i_asy == 0)
//                {
//                    i_point[j] = (_queue_point.PopByte() - 2048) * 12 / 10;
//                    i_point_PM[j] = 0;
//                } else {
//                    i_point_PM[j] = 1;
//
//                }
//
//            }
//            n_start = 1;
//        } else {
//            n_start = 0;
//        }
//    }

    public void fun_zoom2(float xx_zoom, float yy_zoom) {
        YY_zoom = yy_zoom;
        XX_zoom = xx_zoom;
    }

    // ��ֵ
    int[] _InsertBuffer = new int[20];
    int _LashInsertData;

    private void InsertMath(int Value) {
        _InsertBuffer[0] = (_LashInsertData + Value) / 2;
        _InsertBuffer[1] = Value;
        _LashInsertData = Value;
    }

    // Ѫ��
//    public void get_data2() {
//        if (_queue_point2.Count() > n_Total_count) {
//            for (int i = 1; i < n_Total_count;) {
//                InsertMath(_queue_point2.PopByte());
//                i_point2[i++] = _InsertBuffer[0];
//                i_point2[i++] = _InsertBuffer[1];
//            }
//            n_start2 = 1;
//        } else {
//            n_start2 = 0;
//
//        }
//
//    }

    public void onDataReceived(byte[] buffer, int size) {
        // TODO Auto-generated method stub

        Integer count = 0;
        while (count < size && (i_thread_work == 1)) {
            int temp = buffer[count++];
            temp &= 0xff;
            _queue.Push(temp);

            if (i_stop > 100000) {
                i_stop = 0;
            }
            i_stop += count;
        }

    }


    // ��������

    public void fun_start() {
        byte[] by_data = new byte[2];
        by_data[0] = 0x55;
        by_data[1] = 1;
        if (mOutputStream != null) {
            SendData(by_data, 1);
            SendData(by_data, 1);
            Log.v("zhixing","true");
        }else{
            Log.v("zhixing","false");
        }

    }

    // ֹͣ����
    public void fun_stop() {
        byte[] by_data = new byte[2];
        by_data[0] = 0x56;
        by_data[1] = 1;
        if (mOutputStream != null) {
            SendData(by_data, 1);
        }
        i_press = 0;
    }

    // ֹͣ����
    public void stop_nibp() {
        byte[] by_data = new byte[2];
        by_data[0] = 0x56;
        by_data[1] = 1;
        if (mOutputStream != null) {
            SendData(by_data, 1);
        }
    }

    class My_Callback implements SurfaceHolder.Callback {

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // TODO Auto-generated method stub

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }


        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // TODO Auto-generated method stub
            i_thread_work = 0;
        }

    }

    public Map<String, Object> getTvData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("systolic", i_value[glob_systolic]);
        map.put("diastolic", i_value[glob_diastolic]);
        map.put("avg", i_value[glob_avg]);
        map.put("xindian", i_value[glob_heartrate]);
        map.put("xueyang", i_value[glob_osld]);
        map.put("xiudaiya",i_value[glob_xiudai]);
        return map;
    }

    public Map<String,Object> getXinDianData(){
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("xindian",_queue_point);
        return map2;
    }

    public Map<String,Object> getXueYangData(){
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("xueyang",_queue_point2);
        return map3;
    }

    public void closeAndExit() {
        try {
            auto_reset();
            i_thread_work = 0; // �̲߳���ִ��
            Thread.sleep(100);
            if (mOutputStream != null) {
                mOutputStream.close();
                mOutputStream = null;
            }
            if (mSerialPort != null) {
                mSerialPort.close();
                mSerialPort = null;
            }
            if (_queue.Count() > 0) {
                _queue.Empty();
            }
            if (_queue_point.Count() > 0) {
                _queue_point.Empty();
            }

            if (_queue_point2.Count() > 0) {
                _queue_point2.Empty();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void clearQueue(){
        if (_queue.Count() > 0) {
            _queue.Empty();
        }
        if (_queue_point.Count() > 0) {
            _queue_point.Empty();
        }

        if (_queue_point2.Count() > 0) {
            _queue_point2.Empty();
        }
    }

}
