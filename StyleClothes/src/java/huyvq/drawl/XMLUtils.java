/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.drawl;

import static com.sun.activation.registries.LogSupport.log;
import static common.SyntaxState.ATTR_NAME;
import static common.SyntaxState.ATTR_VALUE_NQ;
import static common.SyntaxState.ATTR_VALUE_Q;
import static common.SyntaxState.CLOSE_BRACKET;
import static common.SyntaxState.CLOSE_TAG_NAME;
import static common.SyntaxState.CLOSE_TAG_SLASH;
import static common.SyntaxState.CONTENT;
import static common.SyntaxState.D_QUOT;
import static common.SyntaxState.EMPTY_SLASH;
import static common.SyntaxState.EQ;
import static common.SyntaxState.EQUAL;
import static common.SyntaxState.EQUAL_WAIT;
import static common.SyntaxState.GT;
import static common.SyntaxState.INLINE_TAGS;
import static common.SyntaxState.LT;
import static common.SyntaxState.OPEN_BRACKET;
import static common.SyntaxState.OPEN_TAG_NAME;
import static common.SyntaxState.SLASH;
import static common.SyntaxState.S_QUOT;
import static common.SyntaxState.TAG_INNER;
import static common.SyntaxState.WAIT_END_TAG_CLOSE;
import static common.SyntaxState.isAttrChars;
import static common.SyntaxState.isSpace;
import static common.SyntaxState.isStartAttrChars;
import static common.SyntaxState.isStartTagChars;
import static common.SyntaxState.isTagChars;
import huyvq.registration.Product;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Dell
 */
public class XMLUtils {
    
    public static String marshallToString(Product medicines) {
        try {
            JAXBContext jaxbc = JAXBContext.newInstance(Product.class);
            Marshaller ms = jaxbc.createMarshaller();
            ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter sv = new StringWriter();
            
            ms.marshal(medicines, sv);
            
            return sv.toString();
            
        } catch (Exception e) {
            log("Exception: " + e.getMessage());
        }
        return null;
    }
    public static void parseString(String xml, DefaultHandler handler) {
        try {
            StringReader sr = new StringReader(xml);
            InputSource is = new InputSource(sr);
            //tạo đối tượng cung cấp API
            SAXParserFactory spf = SAXParserFactory.newInstance();
            //
            SAXParser sax = spf.newSAXParser();
            //parse and process
            sax.parse(is, handler);
        } catch (ParserConfigurationException   e) {
            log("Parser " + e.getMessage());
        } catch (SAXException e) {
            log("SAX " + e.getMessage());
        } catch (IOException e) {
            log("IO " + e.getMessage());
        }
    }
    
    public static String getDomain(String url) {
        String domain = url;
        try {
            domain = domain.substring(domain.indexOf("//") + 2);
            domain = domain.substring(0, domain.indexOf("/"));
        } catch (Exception e) {
            log("Exception " + e.getMessage());
        }
        return domain;
    }
    
    public static String getSubDomain(String url) {
        String domain = url;
        domain = domain.substring(domain.indexOf("//") + 2);
        domain = domain.substring(domain.indexOf("/"));
        return domain;
    }
 private static Character quote;

    public static String check(String src) {
        //refine
//        src = refineHtml(src);

        char[] reader = src.toCharArray();
        StringBuilder writer = new StringBuilder();

        StringBuilder openTag = new StringBuilder();
        boolean isEmptyTag = false, isOpenTag = false, isCloseTag = false;
        StringBuilder closeTag = new StringBuilder();
        StringBuilder attrName = new StringBuilder();
        StringBuilder attrValue = new StringBuilder();
        Map<String, String> attributes = new HashMap<>();

        StringBuilder content = new StringBuilder();

        Stack<String> stack = new Stack<>();
        String state = CONTENT;

        for (int i = 0; i < reader.length; i++) {
            char c = reader[i];

            switch (state) {
                case CONTENT:
                    if (c == LT) {
                        state = OPEN_BRACKET;
                        writer.append(content.toString().trim().replace("&", "&amp;"));
                    } else {
                        content.append(c);
                    }
                    break;
                case OPEN_BRACKET:
                    if (isStartTagChars(c)) {
                        state = OPEN_TAG_NAME;

                        isOpenTag = true;
                        isCloseTag = false;
                        isEmptyTag = false;

                        openTag.setLength(0);
                        openTag.append(c);
                    } else if (c == SLASH) {
                        state = CLOSE_TAG_SLASH;

                        isOpenTag = false;
                        isCloseTag = true;
                        isEmptyTag = false;
                    }
                    break;

                case OPEN_TAG_NAME:
                    if (isTagChars(c)) { // loop
                        openTag.append(c);
                    } else if (isSpace(c)) {
                        state = TAG_INNER;

                        attributes.clear();
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    } else if (c == SLASH) {
                        state = EMPTY_SLASH;
                    }
                    break;
                case TAG_INNER:
                    if (isSpace(c)) {

                    } else if (isStartAttrChars(c)) {
                        state = ATTR_NAME;

                        attrName.setLength(0);
                        attrName.append(c);
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    } else if (c == SLASH) {
                        state = EMPTY_SLASH;
                    }
                    break;

                case ATTR_NAME:
                    if (isAttrChars(c)) {
                        attrName.append(c);
                    } else if (c == EQ) {
                        state = EQUAL;
                    } else if (isSpace(c)) {
                        state = EQUAL_WAIT;
                    } else {
                        if (c == SLASH) {
                            attributes.put(attrName.toString(), "true");
                            state = EMPTY_SLASH;
                        } else if (c == GT) {
                            attributes.put(attrName.toString(), "true");
                            state = CLOSE_BRACKET;
                        }
                    }
                    break;
                case EQUAL_WAIT:
                    if (isSpace(c)) {

                    } else if (c == EQ) {
                        state = EQUAL;
                    } else {
                        if (isStartAttrChars(c)) {
                            attributes.put(attrName.toString(), "true");
                            state = ATTR_NAME;

                            attrName.setLength(0);
                            attrName.append(c);
                        }
                    }
                    break;
                case EQUAL:
                    if (isSpace(c)) {

                    } else if (c == D_QUOT || c == S_QUOT) {
                        quote = c;
                        state = ATTR_VALUE_Q;

                        attrValue.setLength(0);
                    } else if (!isSpace(c) && c != GT) {
                        state = ATTR_VALUE_NQ;

                        attrValue.setLength(0);
                        attrValue.append(c);
                    }
                    break;
                case ATTR_VALUE_Q:
                    if (c != quote) {
                        attrValue.append(c);
                    } else if (c == quote) {
                        state = TAG_INNER;
                        attributes.put(attrName.toString(), attrValue.toString());
                    }
                    break;
                case ATTR_VALUE_NQ:
                    if (!isSpace(c) && c != GT) {
                        attrValue.append(c);
                    } else if (isSpace(c)) {
                        state = TAG_INNER;
                        attributes.put(attrName.toString(), attrValue.toString());
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                        attributes.put(attrName.toString(), attrValue.toString());
                    }
                    break;
                case EMPTY_SLASH:
                    if (c == GT) {
                        state = CLOSE_BRACKET;
                        isEmptyTag = true;
                    }
                    break;

                case CLOSE_TAG_SLASH:
                    if (isStartTagChars(c)) {
                        state = CLOSE_TAG_NAME;

                        closeTag.setLength(0);
                        closeTag.append(c);
                    }
                    break;

                case CLOSE_TAG_NAME:
                    if (isTagChars(c)) {
                        closeTag.append(c);
                    } else if (isSpace(c)) {
                        state = WAIT_END_TAG_CLOSE;
                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    }
                    break;

                case WAIT_END_TAG_CLOSE:
                    if (isSpace(c)) {

                    } else if (c == GT) {
                        state = CLOSE_BRACKET;
                    }
                    break;

                case CLOSE_BRACKET:
                    if (isOpenTag) {
                        String openTagName = openTag.toString().toLowerCase();

                        if (INLINE_TAGS.contains(openTagName)) {
                            isEmptyTag = true;
                        }
                        writer.append(LT)
                                .append(openTagName)
                                .append(convert(attributes))
                                .append((isEmptyTag) ? "/" : "")
                                .append(GT);
                        attributes.clear();

                        if (!isEmptyTag) {
                            stack.push(openTagName);
                        }
                    } else if (isCloseTag) {
                        String closeTagName = closeTag.toString().toLowerCase();

                        if (!stack.isEmpty() && stack.contains(closeTagName)) {
                            while (!stack.isEmpty() && !stack.peek().equals(closeTagName)) {
                                writer.append(LT)
                                        .append(SLASH)
                                        .append(stack.pop())
                                        .append(GT);
                            }
                            if (!stack.isEmpty() && stack.peek().equals(closeTagName)) {
                                writer.append(LT)
                                        .append(SLASH)
                                        .append(stack.pop())
                                        .append(GT);
                            }
                        }
                    }
                    if (c == LT) {
                        state = OPEN_BRACKET;
                    } else {
                        state = CONTENT;

                        content.setLength(0);
                        content.append(c);
                    }
                    break;
            }
        }

        if (CONTENT.equals(state)) {
            writer.append(content.toString().trim().replace("&", "&amp;"));
        }

        while (!stack.isEmpty()) {
            writer.append(LT)
                    .append(SLASH)
                    .append(stack.pop())
                    .append(GT);
        }

        return writer.toString();
    }

    //convert tập attr thành cặp key-value, đúng chuẩn xml
    private static String convert(Map<String, String> attributes) {
        if (attributes.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String value = entry.getValue()
                    .replace("&", "&amp;")
                    .replace("\"", "&quot;")
                    .replace("'", "&apos;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;");

            builder.append(entry.getKey())
                    .append("=")
                    .append("\"").append(value).append("\"")
                    .append(" ");
        }

        String result = builder.toString().trim();

        if (!result.equals("")) {
            result = " " + result;
        }
        return result;
    }

    public static String refineHtml(String src) {
        src = getBody(src);
        src = removeNeedlessTags(src);
        src = replaceUnicodeCharacter(src);
        return src;
    }

    private static String getBody(String src) {
        String result = src;

        String exp = "<body.*?</body>";
        Pattern pattern = Pattern.compile(exp);

        Matcher matcher = pattern.matcher(result);
        if (matcher.find()) {
            result = matcher.group(0);
        }
        return result;
    }

    private static String removeNeedlessTags(String src) {
        String result = src;

        String expression = "<script.*?</script>";
        result = result.replaceAll(expression, "");

        expression = "<!--.*?-->";
        result = result.replaceAll(expression, "");

        expression = "<head.*?</head>";
        result = result.replaceAll(expression, "");

        expression = "&nbsp;?";
        result = result.replaceAll(expression, "");

        return result;
    }

    private static String replaceUnicodeCharacter(String src) {
        src = src.replaceAll("\\p{C}", "");

        src = src.replaceAll("&#8211;", "-");

        return src;
    }
    
    public static int computeMatchingPercent(String a, String b) {
        int n = a.length();
        int m = b.length();

        int dp[][] = new int[n + 1][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (a.charAt(i) == b.charAt(j)) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }
        return (int) dp[n][m] * 100 / Math.min(m, m);
    }
}
