package com.sei.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class CheckCodeImage {

    // 宽度
    private int w = 120;
    // 高度
    private int h = 40;
    // 取值
    private String str = "123456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
    Random random = new Random();
    // 验证码
    public String text;


    // 随机字体
    public Font randomFont() {
        String name = "楷体";
        int style = random.nextInt(4);// 生成随机的样式, 0(无样式), 1(粗体), 2(斜体), 3(粗体+斜体)
        int size = random.nextInt(4) + 28;// 字体大小在28~31之间
        Font font = new Font(name, style, size);
        return font;
    }


    // 随机颜色
    public Color randomcColor() {
/**
 * r,g,b三基色都为了便于和背景色区分；都取值在0~155之间
 */
        int r = random.nextInt(155);
        int g = random.nextInt(155);
        int b = random.nextInt(155);
        Color color = new Color(r, g, b);
        return color;
    }


    // 干扰线
    public void drawLines(BufferedImage image) {
// 得到画布
        Graphics2D g = (Graphics2D) image.getGraphics();
        int num = 5;
        for (int i = 0; i < num; i++) {
// 一条线两个点，每个点两个坐标
            int x1 = random.nextInt(w);
            int y1 = random.nextInt(h);
            int x2 = random.nextInt(w);
            int y2 = random.nextInt(h);
            g.setStroke(new BasicStroke(1.5F));
            g.setColor(randomcColor());
            g.drawLine(x1, y1, x2, y2);
        }
    }


    // 绘制图片
    public BufferedImage createImage() {
// 创建图片缓冲区
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bi.getGraphics();
        g.setBackground(Color.WHITE);
        g.fillRect(0, 0, w, h);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String cr = str.charAt(random.nextInt(str.length())) + "";
            sb.append(cr);
            float x = i * 1.0f * w / 4;
// 设置字体旋转角度
            int degree = new Random().nextInt() % 30;
// 正向角度
            g.rotate(degree * Math.PI / 180, x, 25);
// 反向角度
            g.rotate(-degree * Math.PI / 180, x, 25);
// 设置颜色
            g.setColor(randomcColor());
// 设置字体
            g.setFont(randomFont());
            g.drawString(cr, x+5, h - 5);
        }
        text = sb.toString();
        drawLines(bi);
        return bi;
    }


    public static void output(BufferedImage image, OutputStream out)
            throws IOException {
        ImageIO.write(image, "JPEG", out);


    }

}
