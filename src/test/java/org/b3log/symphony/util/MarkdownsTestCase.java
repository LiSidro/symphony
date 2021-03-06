/*
 * Copyright (c) 2012-2016, b3log.org & hacpai.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.b3log.symphony.util;

import java.io.FileReader;
import java.net.URL;
import org.testng.Assert;
import org.apache.commons.io.IOUtils;
import org.b3log.latke.Latkes;
import org.testng.annotations.Test;

/**
 * Markdown utilities test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 2.1.0.0, Aug 31, 2016
 * @since 0.1.6
 */
public class MarkdownsTestCase {

    static {
        Latkes.initRuntimeEnv();
    }

    /**
     * Tests {@link Markdowns#clean(java.lang.String, java.lang.String)} for data XSS.
     */
    @Test
    public void clean() {
        final String md = "<a href='data:text/html;base64,PHNjcmlwdD5hbGVydCgnWFNTJyk8L3NjcmlwdD4K'>a link</a>";

        final String html = Markdowns.toHTML(md);
        final String securedHTML = Markdowns.clean(html, "");

        Assert.assertFalse(securedHTML.contains("href"));
    }

    @Test
    public void space() {
        final String md = "Sym是一个用Java写的实时论坛，欢迎来[体验Sym](https://hacpai.com)！";
        final String html = Markdowns.toHTML(md);

        Assert.assertEquals(html, "<p>Sym 是一个用 Java 写的实时论坛，欢迎来<a href=\"https://hacpai.com\">体验 Sym</a>！</p>");
    }

    /**
     * FIXME: https://github.com/sirthias/pegdown/issues/229
     */
    @Test
    public void toHTML1() {
        final String md = "Sym**是一个用Java写的实时论坛**";
        final String html = Markdowns.toHTML(md);

        System.out.println(html);
    }

    /**
     * Tests {@link Markdowns#toHTML(java.lang.String)}.
     */
    @Test
    public void toHTML() {
        String md = "[b3log](http://b3log.org)";
        String html = Markdowns.toHTML(md);
        Assert.assertTrue(html.contains("href"));

        md = "[b3log](b3log.org)";
        html = Markdowns.toHTML(md);
        Assert.assertTrue(html.contains("href"));
    }

    /**
     * Tests {@link Markdowns#toHTML(java.lang.String)}.
     *
     * @throws java.lang.Exception exception
     */
    @Test
    public void toHtml0() throws Exception {
        final URL mdResource = MarkdownsTestCase.class.getResource("/markdown_syntax.text");
        final String md = IOUtils.toString(new FileReader(mdResource.getPath()));
        final String html = Markdowns.toHTML(md);

        //System.out.println(html);
    }
}
