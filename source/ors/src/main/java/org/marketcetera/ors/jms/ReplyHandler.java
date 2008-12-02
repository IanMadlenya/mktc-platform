package org.marketcetera.ors.jms;

import org.marketcetera.util.misc.ClassVersion;

/**
 * A type-safe message handler that produces replies.
 *
 * @author tlerios@marketcetera.com
 * @since $Release$
 * @version $Id$
 */

/* $License$ */

@ClassVersion("$Id$") //$NON-NLS-1$
public interface ReplyHandler<T>
{
    /**
     * Handles the given message, and returns a reply. Changing the
     * name of this method requires changing the implementation of
     * {@link IncomingJmsFactory} as well.
     *
     * @param msg The message.
     *
     * @return The reply.
     */

    T replyToMessage
        (T msg);
}