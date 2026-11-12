# Usage: .\run.ps1 WS_DATA2        (or WS_DATA2.java, or src/main/java/WS_DATA2.java)
param([Parameter(Mandatory = $true)][string]$File)

$main = [System.IO.Path]::GetFileNameWithoutExtension($File)
Push-Location $PSScriptRoot
try {
    mvn -q -Pcli compile org.codehaus.mojo:exec-maven-plugin:3.1.0:java "-Dexec.mainClass=$main"
} finally {
    Pop-Location
}
