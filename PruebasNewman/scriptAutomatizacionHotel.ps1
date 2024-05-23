Get-ChildItem -Path .\ -Filter Tests_HOTEL.postman_collection.json -File -Name| ForEach-Object {
    if($_ -ne "env.json")
    {
            newman run ($_) -e Variables.postman_environmentTFG.json -r htmlextra
    }
}