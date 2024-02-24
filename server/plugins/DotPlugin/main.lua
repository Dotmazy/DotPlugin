--[[function printChildrenAndTypes(obj, indent)
    indent = indent or 0

    local t = type(obj)

    if t == "table" then
        for k, v in pairs(obj) do
            local childType = type(v)
            local childIndent = string.rep("  ", indent + 1)

            print(childIndent .. k .. " (" .. childType .. ")")

            if childType == "table" then
                printChildrenAndTypes(v, indent + 2)
            end
        end
    else
        print(string.rep("  ", indent) .. tostring(obj) .. " (" .. t .. ")")
    end
end]]--

--printChildrenAndTypes()

--local player = bukkit.getPlayer("Dotmazy")
--player.getName()
--print(bukkit.getPlayer("Dotmazy").getFlySpeed())