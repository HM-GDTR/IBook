package com.rainbowx.finalwork.Activities.settings;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rainbowx.finalwork.R;

import io.noties.markwon.Markwon;

public class DisclaimersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimers);

        Markwon markwon = Markwon.create(DisclaimersActivity.this);

        final String text = "免责声明\n" +
                "\n" +
                "欢迎使用我们的聊天软件 iBook（以下简称“软件”）。iBook是由 IBook团队 开发的一款安卓端聊天应用程序。在使用本软件之前，请仔细阅读以下免责声明，以确保您充分了解我们的服务条款和责任限制。\n" +
                "\n" +
                "1. **服务提供者：**\n" +
                "   - iBook 是 IBook团队 提供的一项服务。本团队保留在任何时候修改、中断或终止软件服务的权利，无论是否提前通知用户。\n" +
                "\n" +
                "2. **用户责任：**\n" +
                "   - 使用本软件的用户应对其在软件上的行为和内容负责。用户应确保其使用本软件的方式不违反任何法律法规，不侵犯他人权益，不发布违法、淫秽、诽谤或其他不适当的内容。\n" +
                "   - 用户需妥善保管账户信息，对于使用其账户进行的一切活动负有责任。\n" +
                "\n" +
                "3. **隐私保护：**\n" +
                "   - IBook团队 将尽力保护用户的隐私信息。我们承诺不会未经用户授权将用户的个人信息透露给第三方。具体隐私政策请参阅我们的隐私声明。\n" +
                "\n" +
                "4. **软件兼容性：**\n" +
                "   - iBook 的使用可能受到特定设备和操作系统的限制。 IBook团队 不保证软件在所有设备和操作系统上都能完全兼容。\n" +
                "\n" +
                "5. **免责声明：**\n" +
                "   - 本软件按“现状”提供， IBook团队 对软件的适用性、准确性、可靠性或完整性不提供任何明示或暗示的担保。\n" +
                "   - IBook团队 不对用户因使用本软件而导致的任何直接或间接损失负责，包括但不限于利润损失、商业中断、信息丢失等。\n" +
                "\n" +
                "6. **服务变更：**\n" +
                "   - IBook团队 保留在任何时候修改、暂停或终止软件服务的权利，恕不另行通知。\n" +
                "\n" +
                "7. **知识产权：**\n" +
                "   - 本软件及其相关内容的知识产权归 IBook团队 所有。未经授权，用户不得复制、修改、传播、发行或进行其他侵犯知识产权的行为。\n" +
                "\n" +
                "请在使用本软件之前详细阅读并理解以上免责声明。如有任何疑问，请联系我们的客户服务团队。通过使用 iBook，即表示您同意并接受本免责声明的全部内容。 IBook团队 保留在任何时候修改免责声明的权利，修改后的声明将在软件内生效。";
        final TextView textView = findViewById(R.id.disTextViewDis);
        markwon.setMarkdown(textView, text);
    }
}
