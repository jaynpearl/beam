/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.beam.examples;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.TypeDescriptors;

public class LineCountLambda {

  public static void main(String[] args) {
    PipelineOptions options = PipelineOptionsFactory.create();
    Pipeline p = Pipeline.create(options);

    p.apply(TextIO.read().from("gs://dataflow-samples/shakespeare/kinglear.txt"))
     .apply(Count.<String>globally())
     .apply(MapElements.into(TypeDescriptors.strings())
                       .via((Long count) -> Long.toString(count)))
     .apply(TextIO.write().to("linecount"));

    p.run().waitUntilFinish();
  }
}
