-- 记录是否抢过的标记key -hash类型
local key     = KEYS[1]
-- 用户ID
local userId = KEYS[2]
-- 存储token信息的list类型
local tokenKey = KEYS[3]
-- 这个暂时没有用
local content = ARGV[1]
-- 看看有没有抢过 通过hash类型判断
local recordSuccess = redis.call('hsetnx', key, userId, content)
if recordSuccess == 0 then
  return 0
else
  -- 获取token，通过list类型获取
  local token = redis.call('rpop', tokenKey)
  if token == false then
    return 0
  else
    return 1
  end
end