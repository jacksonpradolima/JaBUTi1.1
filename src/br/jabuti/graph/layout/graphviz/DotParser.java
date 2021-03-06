/* Generated By:JavaCC: Do not edit this line. DotParser.java */
package br.jabuti.graph.layout.graphviz;

import java.util.*;
import java.util.regex.Matcher;
import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import br.jabuti.graph.view.gvf.GVFNode;
import br.jabuti.graph.view.gvf.GVFLink;

public class DotParser implements DotParserConstants {
                private Vector vnode;

                private Vector vlink;

                private double graphHeight = 0;

                private double graphWidth = 0;

                public DotParser(Vector v, Vector v2, InputStream is)
                {
                                this (is);
                                if (v == null || v2 == null)
                                {
                                                throw new IllegalArgumentException("No nodes or links have been supplied as argument");
                                }
                                vnode = v;
                                vlink = v2;
                }

                private double getDouble(String text)
                {
                        double result;
                        try {
                                result = Double.valueOf(text);
                        } catch (Exception e) {
                                try {
                                        result = Double.valueOf(text.replaceAll(".", ","));
                                } catch (Exception e2) {
                                        result = Double.valueOf(text.replaceAll(",", "."));
                                }
                        }
                        return result;
                }

                private GVFNode findLabel(String x)
                {
                                if (vnode == null)
                                {
                                                return null;
                                }
                                Iterator i = vnode.iterator();
                                while (i.hasNext())
                                {
                                                GVFNode ret = (GVFNode) i.next();
                                                ;
                                                if (x.equals(ret.getSource()))
                                                {
                                                                return ret;
                                                }
                                }
                                return null;
                }

                private GVFLink findLink(String orig, String dest)
                {
                                if (vlink == null)
                                {
                                                return null;
                                }
                                Iterator i = vlink.iterator();
                                while (i.hasNext())
                                {
                                                GVFLink ret = (GVFLink) i.next();
                                                if (orig.equals(ret.getSourceNode().getSource()) && dest.equals(ret.getDestinationNode().getSource()))
                                                {
                                                                return ret;
                                                }
                                }
                                return null;
                }

                private void setPositionPreCheck()
                {
                                if (graphHeight == 0 || graphWidth == 0)
                                {
                                                throw new IllegalArgumentException("The graph height and width must be set before adding nodes or links");
                                }
                }

                private void setPosition(String label, Position pos)
                {
                                GVFNode node = findLabel(label);
                                if (node != null)
                                {
                                                setPositionPreCheck();
                                                node.moveTo((int) pos.getX(), (int) (graphHeight - pos.getY()));
                                }
                }

                private void setLinkPosition(String l1, String l2, List < Position > pos)
                {
                                GVFLink link = findLink(l1, l2);
                                if (link == null)
                                {
                                                return;
                                }
                                Iterator < Position > i = pos.iterator();
                                while (i.hasNext())
                                {
                                                setPositionPreCheck();
                                                Position p = i.next();
                                                link.addPoint((int) p.getX(), (int) (graphHeight - p.getY()));
                                }
                }

                private void setBoundingBox(BoundingBox bb)
                {
                                graphHeight = (int) bb.getHeight();
                                graphWidth = (int) bb.getWidth();
                }

  final public void parse() throws ParseException {
    jj_consume_token(DIGRAPH);
    jj_consume_token(IDENTIFIER);
    jj_consume_token(LBRACE);
    label_1:
    while (true) {
      switch (jj_nt.kind) {
      case GRAPH:
      case NODE:
      case TO:
      case LBRACKET:
      case NODEIDENT:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      switch (jj_nt.kind) {
      case GRAPH:
        graphDef();
        break;
      case NODE:
        defaultNode();
        break;
      default:
        jj_la1[1] = jj_gen;
        if (jj_2_1(2147483647)) {
          linkDef();
        } else {
          switch (jj_nt.kind) {
          case LBRACKET:
          case NODEIDENT:
            nodeDef();
            break;
          default:
            jj_la1[2] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
      }
      jj_consume_token(SEMICOLON);
    }
    jj_consume_token(RBRACE);
  }

  final public void defaultNode() throws ParseException {
    jj_consume_token(NODE);
    atributeList();
  }

  final public void atributeList() throws ParseException {
    jj_consume_token(LBRACKET);
    atribute();
    label_2:
    while (true) {
      switch (jj_nt.kind) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_2;
      }
      jj_consume_token(COMMA);
      atribute();
    }
    jj_consume_token(RBRACKET);
  }

  final public void atribute() throws ParseException {
    jj_consume_token(IDENTIFIER);
    jj_consume_token(EQUALS);
    switch (jj_nt.kind) {
    case STRING:
      jj_consume_token(STRING);
      break;
    case NUMBER:
      jj_consume_token(NUMBER);
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void graphDef() throws ParseException {
                BoundingBox bb = null;
    jj_consume_token(GRAPH);
    jj_consume_token(LBRACKET);
    switch (jj_nt.kind) {
    case BBOX:
      bb = boundingBox();
                                                                setBoundingBox(bb);
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
    label_3:
    while (true) {
      switch (jj_nt.kind) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;
        break label_3;
      }
      jj_consume_token(COMMA);
      switch (jj_nt.kind) {
      case BBOX:
        bb = boundingBox();
                                                                setBoundingBox(bb);
        break;
      default:
        jj_la1[7] = jj_gen;
        ;
      }
    }
    jj_consume_token(RBRACKET);
  }

  final public void nodeDef() throws ParseException {
                Position p = null;
                String l = null;
                Token t = null;
    switch (jj_nt.kind) {
    case NODEIDENT:
      t = jj_consume_token(NODEIDENT);
                                                l = t.image;
      break;
    default:
      jj_la1[8] = jj_gen;
      ;
    }
    jj_consume_token(LBRACKET);
    switch (jj_nt.kind) {
    case IDENTIFIER:
      atribute();
      break;
    default:
      jj_la1[10] = jj_gen;
      switch (jj_nt.kind) {
      case POS:
        p = nodePosition();
                                                                setPosition(l, p);
        break;
      default:
        jj_la1[9] = jj_gen;
        ;
      }
    }
    label_4:
    while (true) {
      switch (jj_nt.kind) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[11] = jj_gen;
        break label_4;
      }
      jj_consume_token(COMMA);
      switch (jj_nt.kind) {
      case IDENTIFIER:
        atribute();
        break;
      default:
        jj_la1[13] = jj_gen;
        switch (jj_nt.kind) {
        case POS:
          p = nodePosition();
                                                                                setPosition(l, p);
          break;
        default:
          jj_la1[12] = jj_gen;
          ;
        }
      }
    }
    jj_consume_token(RBRACKET);
  }

  final public List<Position> linkPosition() throws ParseException {
                Token t = null;
                List<Position> positions = new ArrayList<Position>();
    jj_consume_token(POS);
    jj_consume_token(EQUALS);
    if (jj_2_2(2)) {
      t = jj_consume_token(STRING);
                                String lineBreak = Matcher.quoteReplacement("\u005cn");
                                String carriageReturn = Matcher.quoteReplacement("\u005cr");
                                String endLineSlash = Matcher.quoteReplacement("\u005c\u005c");
                                String quote = Matcher.quoteReplacement("\u005c"");
                                String token = t.image.replaceAll(lineBreak, "").replaceAll(carriageReturn, "").replaceAll(endLineSlash, "").replaceAll(quote, "").substring(2);
                                StringTokenizer st = new StringTokenizer(token, " ");
                                while (st.hasMoreTokens()) {
                                        String s = st.nextToken();
                                        String[] sps = s.split(",");
                                        Position p = new Position(getDouble(sps[0]), getDouble(sps[1]));
                                        positions.add(p);
                                }
    } else {
      ;
    }
                        {if (true) return positions;}
    throw new Error("Missing return statement in function");
  }

  final public Position nodePosition() throws ParseException {
                Token t = null;
    jj_consume_token(POS);
    jj_consume_token(EQUALS);
    t = jj_consume_token(STRING);
                                String[] sps = t.image.replaceAll("\u005c"", "").split(",");
                                Position p = new Position(getDouble(sps[0]), getDouble(sps[1]));
                                {if (true) return p;}
    throw new Error("Missing return statement in function");
  }

  final public BoundingBox boundingBox() throws ParseException {
                Token t = null;
    jj_consume_token(BBOX);
    jj_consume_token(EQUALS);
    t = jj_consume_token(STRING);
                                BoundingBox box = null;
                                String[] values = t.image.replaceAll("\u005c"", "").split(",");
                                double x = getDouble(values[0]);
                                double y = getDouble(values[1]);
                                double width = getDouble(values[2]);
                                double height = getDouble(values[3]);
                                {if (true) return new BoundingBox(x, y, width, height);}
    throw new Error("Missing return statement in function");
  }

  final public void linkDef() throws ParseException {
                String s1 = null;
                String s2 = null;
                Token t1 = null;
                Token t2 = null;
                List < Position > p = null;
    switch (jj_nt.kind) {
    case NODEIDENT:
      t1 = jj_consume_token(NODEIDENT);
                                                s1 = t1.image;
      break;
    default:
      jj_la1[14] = jj_gen;
      ;
    }
    jj_consume_token(TO);
    switch (jj_nt.kind) {
    case NODEIDENT:
      t2 = jj_consume_token(NODEIDENT);
                                                s2 = t2.image;
      break;
    default:
      jj_la1[15] = jj_gen;
      ;
    }
    jj_consume_token(LBRACKET);
    switch (jj_nt.kind) {
    case IDENTIFIER:
      atribute();
      break;
    default:
      jj_la1[17] = jj_gen;
      switch (jj_nt.kind) {
      case POS:
        p = linkPosition();
                                                                setLinkPosition(s1, s2, p);
        break;
      default:
        jj_la1[16] = jj_gen;
        ;
      }
    }
    label_5:
    while (true) {
      switch (jj_nt.kind) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[18] = jj_gen;
        break label_5;
      }
      jj_consume_token(COMMA);
      switch (jj_nt.kind) {
      case IDENTIFIER:
        atribute();
        break;
      default:
        jj_la1[20] = jj_gen;
        switch (jj_nt.kind) {
        case POS:
          p = linkPosition();
                                                                                setLinkPosition(s1, s2, p);
          break;
        default:
          jj_la1[19] = jj_gen;
          ;
        }
      }
    }
    jj_consume_token(RBRACKET);
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_3_2() {
    if (jj_scan_token(STRING)) return true;
    return false;
  }

  private boolean jj_3_1() {
    if (jj_scan_token(NODEIDENT)) return true;
    if (jj_scan_token(TO)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public DotParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[21];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x101c0,0xc0,0x10000,0x80000,0x0,0x800,0x80000,0x800,0x0,0x400,0x0,0x80000,0x400,0x0,0x0,0x0,0x400,0x0,0x80000,0x400,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x80,0x0,0x80,0x0,0x600,0x0,0x0,0x0,0x80,0x0,0x100,0x0,0x0,0x100,0x80,0x80,0x0,0x100,0x0,0x0,0x100,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[2];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public DotParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public DotParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new DotParserTokenManager(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public DotParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new DotParserTokenManager(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public DotParser(DotParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(DotParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    token.next = jj_nt = token_source.getNextToken();
    jj_gen = 0;
    for (int i = 0; i < 21; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken = token;
    if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
    else jj_nt = jj_nt.next = token_source.getNextToken();
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    jj_nt = token;
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if ((token = jj_nt).next != null) jj_nt = jj_nt.next;
    else jj_nt = jj_nt.next = token_source.getNextToken();
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[43];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 21; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 43; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 2; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
