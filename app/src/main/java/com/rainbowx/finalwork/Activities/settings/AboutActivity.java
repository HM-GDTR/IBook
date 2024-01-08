package com.rainbowx.finalwork.Activities.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.rainbowx.finalwork.R;

import io.noties.markwon.Markwon;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        final TextView about = findViewById(R.id.aboutTextViewAbout);
        final String text = "**关于 iBook 聊天软件**\n" +
                "\n" +
                "欢迎使用 iBook，一款由 IBook 团队开发的高度安全且功能强大的聊天应用程序。iBook 为您提供了与朋友、家人和同事进行实时通讯的简便方式，同时注重用户隐私和数据安全。\n" +
                "\n" +
                "**主要特性：**\n" +
                "\n" +
                "1. **即时通讯：** iBook 提供即时的聊天功能，让您与他人保持联系，无论他们身在何处。\n" +
                "\n" +
                "2. **多媒体分享：** 轻松分享照片、视频、语音消息和文件，让沟通更加生动和丰富。\n" +
                "\n" +
                "3. **安全与隐私：** 我们致力于保护您的隐私信息，采用最先进的加密技术，确保您的聊天内容得到安全保护。\n" +
                "\n" +
                "4. **用户友好界面：** iBook 提供直观易用的用户界面，使您能够快速上手并享受愉快的聊天体验。\n" +
                "\n" +
                "5. **定制主题：** 个性化您的聊天界面，选择喜欢的主题和配色方案，使 iBook 成为独一无二的聊天平台。\n" +
                "\n" +
                "**关于 IBook 团队：**\n" +
                "\n" +
                "IBook 团队是一支专业且充满创意的团队，致力于为用户提供出色的移动应用体验。我们将高质量、创新和用户友好性融入到我们的产品中，以满足用户不断变化的需求。\n" +
                "\n" +
                "**联系我们：**\n" +
                "\n" +
                "如果您对 iBook 有任何建议、反馈或问题，我们随时欢迎您与我们联系。请发送邮件至 support@ibookteam.com，我们将竭诚为您服务。\n" +
                "\n" +
                "谢谢您选择 iBook 聊天软件，我们期待为您提供出色的聊天体验！\n" +
                "\n" +
                "*iBook 团队*";

        Markwon markwon = Markwon.create(AboutActivity.this);

        markwon.setMarkdown(about, text);
    }
}