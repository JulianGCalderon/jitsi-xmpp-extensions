package org.jitsi.xmpp.extensions

import org.jivesoftware.smack.packet.XmlEnvironment
import org.jivesoftware.smack.parsing.SmackParsingException
import org.jivesoftware.smack.provider.ExtensionElementProvider
import org.jivesoftware.smack.xml.XmlPullParser
import org.jivesoftware.smack.xml.XmlPullParserException
import java.io.IOException

class TraceParent(val traceId: String, val parentId: String) : AbstractPacketExtension(NAMESPACE, ELEMENT) {
    init {
        setAttribute(TRACE_ID_ATTR_NAME, traceId)
        setAttribute(PARENT_ID_ATTR_NAME, parentId)
    }

    companion object {
        const val ELEMENT = "traceparent"
        const val NAMESPACE = "opentelemetry"
        const val TRACE_ID_ATTR_NAME = "trace_id"
        const val PARENT_ID_ATTR_NAME = "parent_id"
    }
}

class TraceParentProvider : ExtensionElementProvider<TraceParent>() {
    @Throws(XmlPullParserException::class, IOException::class, SmackParsingException::class)
    override fun parse(parser: XmlPullParser, depth: Int, xml: XmlEnvironment?): TraceParent {
        val traceId = parser.getAttributeValue("", TraceParent.TRACE_ID_ATTR_NAME)
            ?: throw SmackParsingException.RequiredAttributeMissingException(
                "Missing '${TraceParent.TRACE_ID_ATTR_NAME}' attribute"
            )
        val parentId = parser.getAttributeValue("", TraceParent.PARENT_ID_ATTR_NAME)
            ?: throw SmackParsingException.RequiredAttributeMissingException(
                "Missing '${TraceParent.PARENT_ID_ATTR_NAME}' attribute"
            )
        return TraceParent(traceId, parentId)
    }
}
