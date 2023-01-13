// Copyright 2022 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package dev.cel.common.internal;

import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkPositionIndexes;

import com.google.common.annotations.VisibleForTesting;
import com.google.errorprone.annotations.Immutable;
import dev.cel.common.annotations.Internal;

/**
 * An array of code points that contain code points from both the Latin-1 character set and Basic
 * Multilingual Plane.
 *
 * <p>CEL Library Internals. Do Not Use.
 */
@Immutable
@VisibleForTesting
@Internal
public final class BasicCodePointArray extends CelCodePointArray {

  @SuppressWarnings("Immutable")
  private final char[] codePoints;

  private final int offset;
  private final int size;

  BasicCodePointArray(char[] codePoints, int size) {
    this(codePoints, 0, size);
  }

  BasicCodePointArray(char[] codePoints, int offset, int size) {
    this.codePoints = checkNotNull(codePoints);
    this.offset = offset;
    this.size = size;
  }

  @Override
  public BasicCodePointArray slice(int i, int j) {
    checkPositionIndexes(i, j, size());
    return new BasicCodePointArray(codePoints, offset + i, j - i);
  }

  @Override
  public int get(int index) {
    checkElementIndex(index, size());
    return codePoints[offset + index] & 0xffff;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public String toString() {
    return new String(codePoints, offset, size);
  }
}
