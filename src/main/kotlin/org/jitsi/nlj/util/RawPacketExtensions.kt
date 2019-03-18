/*
 * Copyright @ 2018 - present 8x8, Inc.
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

package org.jitsi.nlj.util

import org.jitsi.rtp.Packet
import org.jitsi.rtp.NewRawPacket
import org.jitsi.rtp.rtp.RtpPacket
import org.jitsi.service.neomedia.RawPacket


fun Packet.toLegacyRawPacket(): RawPacket =
    RawPacket(buffer, offset, length)

fun fromLegacyRawPacket(legacyRawPacket: RawPacket): NewRawPacket =
    NewRawPacket(legacyRawPacket.buffer, legacyRawPacket.offset, legacyRawPacket.length)

fun RawPacket.toRtpPacket(): RtpPacket =
    RtpPacket(buffer, offset, length)

fun NewRawPacket.shiftPayloadLeft(numBytes: Int) {
    val payloadOffset = payloadOffset
    for (i in payloadOffset until length) {
        buffer[i - numBytes] = buffer[i]
    }
}

fun NewRawPacket.shiftPayloadRight(numBytes: Int) {
    val originalPayloadOffset = payloadOffset
    val originalPayloadLength = payloadLength
    if (payloadOffset + payloadLength + numBytes > length) {
        grow(numBytes)
    }
    for (i in (originalPayloadOffset + originalPayloadLength) downTo originalPayloadOffset) {
        buffer[i + numBytes] = buffer[i]
    }
}