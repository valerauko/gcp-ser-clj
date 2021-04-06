# GCP Simple Error Reporting 🍻 for Clojure

[![Clojars Project](https://img.shields.io/clojars/v/gcp-ser-clj.svg)](https://clojars.org/gcp-ser-clj) [![cljdoc badge](https://cljdoc.org/badge/gcp-ser-clj/gcp-ser-clj)](https://cljdoc.org/d/gcp-ser-clj/gcp-ser-clj/CURRENT)

>[ser](https://en.wiktionary.org/wiki/ser#Hungarian)
>
>Noun, Hungarian
>
>[ˈʃɛr] (_countable and uncountable, plural serek_)
>
>(_archaic, dialectal, humorous_) Alternative form of sör (“beer”).

A Clojure library trying to make reporting errors to Google Cloud's Error Reporting system (Stackdriver) simpler.

## Usage

### Using `report`

You can use the `report` directly to easily report an error to GCP.

```clojure
(try
  (throw (ex-info "Something bad happened" {:important :data}))
  (catch Throwable ex
    (report ex))
```

### Using `with-error-reporting`

`with-error-reporting` makes reporting even easier, as it automatically reports the exceptions that occur in its scope. It re-throws the exceptions so you can decide how to handle them in your application.

```clojure
(with-error-reporting
  (throw (ex-info "Something bad happened" {:important :data})))
```

## License

Copyright © 2021 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
